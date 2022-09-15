package me.dasyad.rickandmortyandroid.rest

import android.graphics.Bitmap
import me.dasyad.rickandmortyandroid.model.Character
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface CharacterApiClient {
    @GET("character")
    suspend fun getAllCharacters(@Query("page") page: Int = 1): CharacterList

    @GET
    suspend fun getImageBitmap(@Url url: String): Bitmap
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
    val next: String?,
    val prev: String?,
)