package com.example.pokedex.repository

import com.example.pokedex.data.local.PokeFavDao
import com.example.pokedex.data.models.PokedexListEntry
import com.example.pokedex.data.remote.PokeApi
import com.example.pokedex.data.remote.responses.Pokemon
import com.example.pokedex.data.remote.responses.PokemonList
import com.example.pokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi,
    private val pokeFavDao: PokeFavDao
) {

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList>{
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception){
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon>{
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception){
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    suspend fun upsertPokeFav(pokemon: PokedexListEntry) {
        pokeFavDao.upsert(pokemon)
    }

    suspend fun deletePokeFav(pokemon: PokedexListEntry) {
        pokeFavDao.delete(pokemon)
    }

    fun getListPokeFav(): Flow<List<PokedexListEntry>> {
        return pokeFavDao.getListPokemon()
    }

    suspend fun getPokeFav(number: Int): PokedexListEntry? {
        return pokeFavDao.getPokemon(number)
    }
}