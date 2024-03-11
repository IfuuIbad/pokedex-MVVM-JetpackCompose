package com.example.pokedex.di

import android.app.Application
import androidx.room.Room
import com.example.pokedex.data.local.PokeFavDao
import com.example.pokedex.data.local.PokedexDatabase
import com.example.pokedex.data.remote.PokeApi
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.util.Constants
import com.example.pokedex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi,
        pokeFavDao: PokeFavDao
    ) = PokemonRepository(api, pokeFavDao)

    @Singleton
    @Provides
    fun  providePokeApi(): PokeApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsUseCases(
        application: Application
    ): PokedexDatabase{
        return Room.databaseBuilder(
            context = application,
            klass = PokedexDatabase::class.java,
            name = Constants.DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePokeFavDao(
        pokedexDatabase: PokedexDatabase
    ): PokeFavDao = pokedexDatabase.pokeFavDao
}