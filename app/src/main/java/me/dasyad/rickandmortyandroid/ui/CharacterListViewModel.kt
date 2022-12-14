package me.dasyad.rickandmortyandroid.ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import me.dasyad.rickandmortyandroid.model.Character
import me.dasyad.rickandmortyandroid.rest.CharacterApiClient

class CharacterListViewModel(
    private val characterApiClient: CharacterApiClient
) : ViewModel() {
    private var maxPages: Int = -1
    private var lastPageIndex = 1

    private val mutexCharacters = Mutex()
    private val mCharacters: MutableLiveData<List<Character>> = MutableLiveData(listOf())
    val characters: LiveData<List<Character>> = mCharacters

    private val mutexBitmaps = Mutex()
    private val mImageBitmaps: MutableLiveData<List<Bitmap>> = MutableLiveData((listOf()))
    val imageBitmaps = mImageBitmaps

    fun hasMore() = lastPageIndex <= maxPages

    suspend fun init() {
        val characterList = characterApiClient.getAllCharacters(lastPageIndex)
        this.maxPages = characterList.info.pages
        loadMoreCharacters()
    }

    suspend fun loadMoreCharacters() {
        if (!hasMore()) {
            return
        }

        mutexCharacters.lock()
        val list = this.characters.value!!.toMutableList()
        val nextList = characterApiClient.getAllCharacters(lastPageIndex).results
        list.addAll(nextList)
        mCharacters.value = list
        lastPageIndex++
        mutexCharacters.unlock()

        mutexBitmaps.lock()
        val bitmaps = this.imageBitmaps.value!!.toMutableList()
        for (character in nextList) {
            bitmaps.add(this.getImageBitmap(character.image))
            mImageBitmaps.value = bitmaps.toList()
        }
        mutexBitmaps.unlock()

    }

    private suspend fun getImageBitmap(url: String): Bitmap =
        characterApiClient.getImageBitmap(url)
}