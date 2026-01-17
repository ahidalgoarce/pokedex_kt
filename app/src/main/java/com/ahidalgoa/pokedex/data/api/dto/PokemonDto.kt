package com.ahidalgoa.pokedex.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// --- DTO for Generation Endpoint (/generation/{id}) ---

@JsonClass(generateAdapter = true)
data class GenerationResponse(
    @field:Json(name = "pokemon_species") val pokemonSpecies: List<PokemonSpeciesEntry>
)

@JsonClass(generateAdapter = true)
data class PokemonSpeciesEntry(
    val name: String,
    val url: String
)

// --- DTO for Pokemon Detail Endpoint (/pokemon/{id}) ---

@JsonClass(generateAdapter = true)
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSprites,
    val stats: List<PokemonStat>,
    val types: List<PokemonType>,
    val cries: Cries? // Added this
)

@JsonClass(generateAdapter = true)
data class Cries(
    val latest: String?,
    val legacy: String?
)

@JsonClass(generateAdapter = true)
data class PokemonSprites(
    @field:Json(name = "other") val other: OtherSprites
)

@JsonClass(generateAdapter = true)
data class OtherSprites(
    @field:Json(name = "official-artwork") val officialArtwork: OfficialArtwork
)

@JsonClass(generateAdapter = true)
data class OfficialArtwork(
    @field:Json(name = "front_default") val frontDefault: String?
)

@JsonClass(generateAdapter = true)
data class PokemonStat(
    @field:Json(name = "base_stat") val baseStat: Int,
    val stat: StatInfo
)

@JsonClass(generateAdapter = true)
data class StatInfo(
    val name: String
)

@JsonClass(generateAdapter = true)
data class PokemonType(
    val slot: Int,
    val type: TypeInfo
)

@JsonClass(generateAdapter = true)
data class TypeInfo(
    val name: String
)

// --- DTO for Species Endpoint (/pokemon-species/{id}) ---

@JsonClass(generateAdapter = true)
data class PokemonSpeciesResponse(
    @field:Json(name = "evolution_chain") val evolutionChain: EvolutionChainUrl?,
    @field:Json(name = "flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>
)

@JsonClass(generateAdapter = true)
data class EvolutionChainUrl(
    val url: String
)

@JsonClass(generateAdapter = true)
data class FlavorTextEntry(
    @field:Json(name = "flavor_text") val flavorText: String,
    val language: Language
)

@JsonClass(generateAdapter = true)
data class Language(
    val name: String
)

// --- DTO for Evolution Chain Endpoint (dynamic URL) ---

@JsonClass(generateAdapter = true)
data class EvolutionChainResponse(
    val chain: ChainLink
)

@JsonClass(generateAdapter = true)
data class ChainLink(
    val species: PokemonSpeciesEntry,
    @field:Json(name = "evolves_to") val evolvesTo: List<ChainLink>
)
