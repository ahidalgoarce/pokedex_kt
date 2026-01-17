package com.ahidalgoa.pokedex.domain.util

/**
 * A generic class that holds a value with its loading status.
 * @param <T> The type of the data.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}
