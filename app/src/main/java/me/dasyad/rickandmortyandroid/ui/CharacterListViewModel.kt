package me.dasyad.rickandmortyandroid.ui

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

    suspend fun init() {
        this.mCharacters.value = characterService.getAllCharacters()
    }
}