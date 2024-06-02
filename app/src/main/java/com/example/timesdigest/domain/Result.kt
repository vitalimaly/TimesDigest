package com.example.timesdigest.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable) : Result<Nothing>
    data object Loading : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> = map<T, Result<T>> { Result.Success(it) }
    .onStart { emit(Result.Loading) }
    .catch { emit(Result.Error(it)) }

fun <T> asFlowResult(block: suspend () -> T): Flow<Result<T>> {
    return flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(block.invoke()))
        } catch (e: Throwable) {
            emit(Result.Error(e))
        }
    }
}
