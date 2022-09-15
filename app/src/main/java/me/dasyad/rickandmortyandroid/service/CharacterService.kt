package me.dasyad.rickandmortyandroid.service

import me.dasyad.rickandmortyandroid.model.Character

interface CharacterService {
    suspend fun getAllCharacters(): List<Character>
}