package ru.fivefourtyfive.wikimapper.util

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun <A, B> Iterable<A>.parallelMap(f: suspend (A) -> B): List<B> = coroutineScope { map { async { f(it) } }.awaitAll() }

fun <T> Boolean.ifTrue(block: () -> T): T? = when (this) {
    true -> block()
    false -> null
}

fun <T> Boolean.ifFalse(block: () -> T): T? = this.not().ifTrue(block)

fun decimalIpToString(decimal: Long) = ((decimal shr 24 and 0xFF).toString() + "."
        + (decimal shr 16 and 0xFF) + "."
        + (decimal shr 8 and 0xFF) + "."
        + (decimal and 0xFF))