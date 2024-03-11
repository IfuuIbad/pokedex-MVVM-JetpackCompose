package com.example.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedex.data.models.PokedexListEntry

@Database(entities = [PokedexListEntry::class], version = 1, exportSchema = false)
abstract class PokedexDatabase: RoomDatabase() {

    abstract val pokeFavDao: PokeFavDao
}