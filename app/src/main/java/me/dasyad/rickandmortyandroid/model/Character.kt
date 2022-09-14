package me.dasyad.rickandmortyandroid.model

/**
 * @see <a href="https://rickandmortyapi.com/documentation/#character-schema">Schema</a>
 */
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Object,
    val location: Object,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String // time
)
