package ru.fivefourtyfive.objectdetails.presentation.ui.util

import ru.fivefourtyfive.wikimapper.domain.entity.Tag

/** Later it is supposed to create a bunch of views, possibly clickable. */
object TagsBuilder {

    fun buildWith(tags: List<Tag>?) = tags?.let {
        buildString {
            append(" || ")
            tags.indices.map { append(tags[it].title).append(" || ") }
        }
    } ?: ""
}