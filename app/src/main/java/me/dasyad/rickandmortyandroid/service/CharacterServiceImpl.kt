package me.dasyad.rickandmortyandroid.service

import android.graphics.Bitmap
import me.dasyad.rickandmortyandroid.model.Character
import me.dasyad.rickandmortyandroid.rest.CharacterApiClient

class CharacterServiceImpl(
    private val characterApiClient: CharacterApiClient
) : CharacterService {
    override suspend fun getAllCharacters(): List<Character> {
        val characterList = characterApiClient.getAllCharacters()
        val list = characterList.results.toMutableList()
        for (i in 1..characterList.info.pages) {
            list.addAll(characterApiClient.getAllCharacters(i).results)
        }
        return list
    }

    override suspend fun getImageBitmap(url: String): Bitmap =
        characterApiClient.getImageBitmap(url)
}