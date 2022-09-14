package me.dasyad.rickandmortyandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import me.dasyad.rickandmortyandroid.databinding.ActivityMainBinding
import me.dasyad.rickandmortyandroid.rest.CharacterApiClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val moshi = createMoshi()
        val retrofit = createRetrofit(moshi)
        val characterApiClient = retrofit.create(CharacterApiClient::class.java)

        val button = binding.startButton
        val textView = binding.textView

        button.setOnClickListener {
            lifecycleScope.launch {
                val characters = characterApiClient.getAllCharacters().results
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