package com.ahidalgoa.pokedex.di

import com.ahidalgoa.pokedex.data.repository.FakePokemonRepository
import com.ahidalgoa.pokedex.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class] // We replace the real AppModule
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideFakePokemonRepository(): PokemonRepository {
        return FakePokemonRepository()
    }
}
