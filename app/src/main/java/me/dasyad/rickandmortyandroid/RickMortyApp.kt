package me.dasyad.rickandmortyandroid

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import me.dasyad.rickandmortyandroid.rest.BitmapConverterFactory
import me.dasyad.rickandmortyandroid.rest.CharacterApiClient
import me.dasyad.rickandmortyandroid.ui.CharacterListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class RickMortyApp : Application() {
    private val moshi = createMoshi()
    private val retrofit = createRetrofit(moshi)

    private val appModules = module {
        factory<CharacterApiClient> { retrofit.create() }

        viewModel { CharacterListViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RickMortyApp)
            modules(appModules)
        }
    }

    private fun createMoshi() = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private fun createRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(Config.BASE_URL)
        .addConverterFactory(BitmapConverterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}