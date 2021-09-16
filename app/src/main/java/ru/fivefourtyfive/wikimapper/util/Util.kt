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