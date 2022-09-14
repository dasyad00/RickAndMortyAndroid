package me.dasyad.rickandmortyandroid.rest

import me.dasyad.rickandmortyandroid.model.Character
import retrofit2.http.GET


interface CharacterApiClient {
    @GET("character")
    suspend fun getAllCharacters(): CharacterList
}

/**
 * @see <a href="https://rickandmortyapi.com/documentation/#get-all-characters">Schema</a>
 */
data class CharacterList(
    val info: CharacterListInfo,
    val results: List<Character>
)

data class CharacterListInfo(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String?,
)