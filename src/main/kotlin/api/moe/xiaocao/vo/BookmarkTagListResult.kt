package api.moe.xiaocao.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import api.moe.xiaocao.model.BookmarkTag

@Serializable
data class BookmarkTagListResult(
    @SerialName("bookmark_tags")
    val bookmarkTags: List<BookmarkTag>,
)
