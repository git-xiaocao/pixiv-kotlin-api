package me.xiaocao.dto

import me.xiaocao.entity.Comment

import kotlinx.serialization.Serializable

@Serializable
data class AddComment(
    val comment: Comment
)