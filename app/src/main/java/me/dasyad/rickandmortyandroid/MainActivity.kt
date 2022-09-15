package me.dasyad.rickandmortyandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import me.dasyad.rickandmortyandroid.databinding.ActivityMainBinding
import me.dasyad.rickandmortyandroid.model.Character
import me.dasyad.rickandmortyandroid.rest.CharacterApiClient
import me.dasyad.rickandmortyandroid.service.CharacterServiceImpl
import me.dasyad.rickandmortyandroid.ui.CharacterListAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val moshi = createMoshi()
    private val retrofit = createRetrofit(moshi)

    private val characterApiClient = retrofit.create(CharacterApiClient::class.java)
    private val characterService = CharacterServiceImpl(characterApiClient)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter = CharacterListAdapter()
        binding.characterList.layoutManager = LinearLayoutManager(this)
        binding.characterList.adapter = adapter

        binding.startButton.setOnClickListener {
            lifecycleScope.launch {
                val characters = characterService.getAllCharacters()
                adapter.setCharacters(characters)
                Log.d("RickMortyAndroid", characters.toString())
            }
        }
    }

    private fun createMoshi() = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private fun createRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(Config.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}