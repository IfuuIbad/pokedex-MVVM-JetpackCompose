package com.example.pokedex.component.pokemonDetail

import com.example.pokedex.data.models.PokedexListEntry

sealed class PokemonDetailEvent {
    data class UpsertDeleteFavorite(val pokemon: PokedexListEntry): PokemonDetailEvent()

    object RemoveSideEffect : PokemonDetailEvent()
}