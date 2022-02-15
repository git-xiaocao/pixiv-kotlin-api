package me.xiaocao.entity

import kotlinx.serialization.Serializable

@Serializable
data class BookmarkTag(
    val name: String,
    val title: String,
)
