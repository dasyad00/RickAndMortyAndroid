package me.dasyad.rickandmortyandroid.ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import me.dasyad.rickandmortyandroid.model.Character
import me.dasyad.rickandmortyandroid.rest.CharacterApiClient

class CharacterListViewModel(
    private val characterApiClient: CharacterApiClient
) : ViewModel() {
    private var maxPages: Int = -1
    private var lastPageIndex = 1

    private val mCharacters: MutableLiveData<List<Character>> = MutableLiveData(listOf())
    val characters: LiveData<List<Character>> = mCharacters

    private val mImageBitmaps: MutableLiveData<List<Bitmap>> = MutableLiveData((listOf()))
    val imageBitmaps = mImageBitmaps

    suspend fun init() {
        val characterList = characterApiClient.getAllCharacters(lastPageIndex)
        this.maxPages = characterList.info.pages
        loadMoreCharacters()
    }

    suspend fun loadMoreCharacters() {
        if (lastPageIndex > maxPages) {
            return
        }

        val list = this.characters.value!!.toMutableList()
        val nextList = characterApiClient.getAllCharacters(lastPageIndex).results
        list.addAll(nextList)
        mCharacters.value = list

        val bitmaps = this.imageBitmaps.value!!.toMutableList()
        val newBitmaps = nextList.map { character ->
            this.getImageBitmap(character.image)
        }
        bitmaps.addAll(newBitmaps)
        mImageBitmaps.value = bitmaps

        lastPageIndex++
    }

    private suspend fun getImageBitmap(url: String): Bitmap =
        characterApiClient.getImageBitmap(url)
}