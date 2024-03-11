package com.example.pokedex.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.pokedex.data.models.PokedexListEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface PokeFavDao {

    @Upsert
    suspend fun upsert(pokemon: PokedexListEntry)

    @Delete
    suspend fun delete(pokemon: PokedexListEntry)

    @Query("SELECT * FROM poke_fav")
    fun getListPokemon(): Flow<List<PokedexListEntry>>

    @Query("SELECT * FROM poke_fav WHERE number=:number")
    suspend fun getPokemon(number: Int): PokedexListEntry?
}