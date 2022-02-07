package ru.fivefourtyfive.places.domain.entity.dto

import ru.fivefourtyfive.places.domain.entity.Comment

class CommentDTO(comment: Comment) {

    val userPhoto: String = comment.userPhoto ?: ""
    val name: String = comment.name ?: ""
    val message: String = comment.message ?: ""
    val date: Long = comment.date ?: 0L

}