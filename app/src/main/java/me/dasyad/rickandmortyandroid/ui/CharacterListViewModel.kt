package me.dasyad.rickandmortyandroid.ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.dasyad.rickandmortyandroid.model.Character
import me.dasyad.rickandmortyandroid.service.CharacterService

class CharacterListViewModel(
    private val characterService: CharacterService
) : ViewModel() {
    private val mCharacters: MutableLiveData<List<Character>> = MutableLiveData(listOf())
    val characters: LiveData<List<Character>> = mCharacters

    private val mImageBitmaps: MutableLiveData<List<Bitmap>> = MutableLiveData((listOf()))
    val imageBitmaps = mImageBitmaps

    suspend fun init() {
        this.mCharacters.value = characterService.getAllCharacters()
        this.imageBitmaps.value = this.characters.value!!.map { character ->
            characterService.getImageBitmap(character.image)
        }
    }
}