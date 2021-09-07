package ru.fivefourtyfive.objectdetails.presentation.ui

import ru.fivefourtyfive.wikimapper.domain.entity.Tag

object TagConverter {
    fun convert(tags: List<Tag>?): String {
        tags?.let {
            return buildString {
                with(tags.indices) {
                    map {
                        append(tags[it].title)
                        if (it < last) append("//")
                    }
                }
            }
        }
        return ""
    }
}