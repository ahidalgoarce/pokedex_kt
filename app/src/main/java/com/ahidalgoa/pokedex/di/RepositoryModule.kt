package com.ahidalgoa.pokedex.di

import com.ahidalgoa.pokedex.data.repository.PokemonRepositoryImpl
import com.ahidalgoa.pokedex.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl): PokemonRepository
}
