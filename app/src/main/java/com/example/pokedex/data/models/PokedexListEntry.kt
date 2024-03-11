package com.example.pokedex.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("poke_fav")
data class PokedexListEntry (
    val pokemonName : String,
    val imageUrl : String,
    @PrimaryKey val number: Int,
)