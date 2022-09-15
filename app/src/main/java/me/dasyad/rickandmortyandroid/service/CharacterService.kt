package me.dasyad.rickandmortyandroid.service

import android.graphics.Bitmap
import me.dasyad.rickandmortyandroid.model.Character

interface CharacterService {
    suspend fun getAllCharacters(): List<Character>
    suspend fun getImageBitmap(url: String): Bitmap
}