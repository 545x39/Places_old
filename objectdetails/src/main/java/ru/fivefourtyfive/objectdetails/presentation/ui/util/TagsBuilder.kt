package ru.fivefourtyfive.objectdetails.presentation.ui.util

/** Later it is supposed to create a bunch of views, possibly clickable. */
object TagsBuilder {

    fun buildWith(tags: List<String>) =
        when (tags.isEmpty()) {
            true -> ""
            false -> {
                buildString {
                    append(" || ")
                    tags.indices.map { append(tags[it]).append(" || ") }
                }
            }
        }
}