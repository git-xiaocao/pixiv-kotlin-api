package me.xiaocao.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.xiaocao.entity.BookmarkTag

@Serializable
data class BookmarkTags(
    @SerialName("bookmark_tags")
    val bookmarkTags: List<BookmarkTag>,
)