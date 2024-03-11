package com.example.pokedex.component.pokemonDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.models.PokedexListEntry
import com.example.pokedex.data.remote.responses.Pokemon
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.util.Resource
import com.example.pokedex.util.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    var isfavorite = mutableStateOf(false)

    suspend fun getFavorite(number: Int) {
        val pokemon = repository.getPokeFav(number)
        if (pokemon == null){
            isfavorite.value = false
        }else{
            isfavorite.value = true
        }
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon>{
        return repository.getPokemonInfo(pokemonName)
    }

    fun onEvent(event: PokemonDetailEvent){
        when(event){
            is PokemonDetailEvent.RemoveSideEffect -> {
                sideEffect = null
            }
            is PokemonDetailEvent.UpsertDeleteFavorite -> {
                viewModelScope.launch {
                    val existPokemon = repository.getPokeFav(event.pokemon.number)
                    if(existPokemon == null){
                        upsertPokemon(event.pokemon)
                        isfavorite.value = true
                    }else{
                        deletePokemon(event.pokemon)
                        isfavorite.value = false
                    }
                }
            }
        }
    }

    private suspend fun deletePokemon(pokemon: PokedexListEntry) {
        repository.deletePokeFav(pokemon)
        sideEffect = UIComponent.Toast("Deleted from Favorite")
    }

    private suspend fun upsertPokemon(pokemon: PokedexListEntry) {
        repository.upsertPokeFav(pokemon)
        sideEffect = UIComponent.Toast("Added to Favorite")
    }


}