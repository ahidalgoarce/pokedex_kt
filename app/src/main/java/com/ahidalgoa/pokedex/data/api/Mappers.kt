package com.ahidalgoa.pokedex.data.api

import com.ahidalgoa.pokedex.data.api.dto.ChainLink
import com.ahidalgoa.pokedex.data.api.dto.GenerationResponse
import com.ahidalgoa.pokedex.data.api.dto.PokemonDetailResponse
import com.ahidalgoa.pokedex.data.api.dto.PokemonSpeciesResponse
import com.ahidalgoa.pokedex.domain.model.Pokemon
import com.ahidalgoa.pokedex.domain.model.PokemonDetail
import com.ahidalgoa.pokedex.domain.model.PokemonEvolution
import com.ahidalgoa.pokedex.domain.model.PokemonStat

private const val POKEMON_IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
private const val ENGLISH_LANG = "en"

// Extracts the Pokémon ID from the URL (e.g., ".../pokemon-species/1/")
private fun getPokemonIdFromUrl(url: String): Int = url.trimEnd('/').substringAfterLast('/').toInt()

/**
 * Maps the response from the generation endpoint to a list of domain [Pokemon] objects.
 */
fun GenerationResponse.toDomain(): List<Pokemon> {
    return pokemonSpecies.map { entry ->
        val id = getPokemonIdFromUrl(entry.url)
        Pokemon(
            id = id,
            name = entry.name.replaceFirstChar { it.uppercase() },
            imageUrl = "$POKEMON_IMAGE_URL$id.png"
        )
    }.sortedBy { it.id }
}

/**
 * Maps the detailed Pokémon response and species info to a domain [PokemonDetail] object.
 */
fun PokemonDetailResponse.toDomain(speciesResponse: PokemonSpeciesResponse, evolutionChain: List<PokemonEvolution>): PokemonDetail {
    val englishDescription = speciesResponse.flavorTextEntries
        .firstOrNull { it.language.name == ENGLISH_LANG }?.flavorText
        ?.replace('\n', ' ') ?: "No description available."

    return PokemonDetail(
        id = this.id,
        name = this.name.replaceFirstChar { it.uppercase() },
        height = this.height / 10f, // From decimeters to meters
        weight = this.weight / 10f, // From hectograms to kilograms
        imageUrl = this.sprites.other.officialArtwork.frontDefault,
        stats = this.stats.map {
            PokemonStat(
                name = it.stat.name.replaceFirstChar { char -> char.uppercase() },
                value = it.baseStat
            )
        },
        types = this.types.sortedBy { it.slot }.map { it.type.name.replaceFirstChar { char -> char.uppercase() } },
        description = englishDescription,
        evolutionChain = evolutionChain,
        cryUrl = this.cries?.latest
    )
}

/**
 * Recursively maps the evolution chain DTO to a flat list of domain [PokemonEvolution] objects.
 */
fun ChainLink.toDomain(): List<PokemonEvolution> {
    val evolutions = mutableListOf<PokemonEvolution>()
    var currentLink: ChainLink? = this

    while (currentLink != null) {
        val id = getPokemonIdFromUrl(currentLink.species.url)
        evolutions.add(
            PokemonEvolution(
                id = id,
                name = currentLink.species.name.replaceFirstChar { it.uppercase() }
            )
        )
        currentLink = currentLink.evolvesTo.firstOrNull()
    }
    return evolutions
}
