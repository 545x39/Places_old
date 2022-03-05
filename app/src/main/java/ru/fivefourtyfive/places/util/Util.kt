package ru.fivefourtyfive.places.util

import android.view.View
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.PrintWriter
import java.io.StringWriter

suspend fun <A, B> Iterable<A>.parallelMap(f: suspend (A) -> B): List<B> =
    coroutineScope { map { async { f(it) } }.awaitAll() }

inline fun <T> Boolean.ifTrue(block: () -> T): T? = when (this) {
    true -> block()
    false -> null
}

inline fun <T> Boolean.ifFalse(block: () -> T): T? = this.not().ifTrue(block)

fun decimalIpToString(decimal: Long) = ((decimal shr 24 and 0xFF).toString() + "."
        + (decimal shr 16 and 0xFF) + "."
        + (decimal shr 8 and 0xFF) + "."
        + (decimal and 0xFF))

/**
 * @param exception Экземпляр исключения
 * @return Стек трейс в виде строки.
 * @brief Возвращает строку со стек трейсом переданного в параметр исключения. Удобен для вывода стек трейса в лог.
 */
fun stackTraceToString(exception: Throwable): String {
    val stringWriter = StringWriter()
    exception.apply {
        printStackTrace(PrintWriter(stringWriter))
        printStackTrace()
    }
    return stringWriter.toString()
}


//@ExperimentalCoroutinesApi
@Suppress("EXPERIMENTAL_API_USAGE")
fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        trySend(Unit)
    }
    awaitClose { setOnClickListener(null) }
}

//@ExperimentalCoroutinesApi
@Suppress("EXPERIMENTAL_API_USAGE")
fun View.longClicks(): Flow<Unit> = callbackFlow {
    setOnLongClickListener {
        @Suppress("DEPRECATION")
        return@setOnLongClickListener offer(Unit)
    }
    awaitClose { setOnLongClickListener(null) }
}

//@ExperimentalCoroutinesApi
fun <T> Flow<T>.throttleFirst(duration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        System.currentTimeMillis().let {
            if (it - lastEmissionTime > duration) {
                lastEmissionTime = it
                emit(upstream)
            }
        }
    }
}