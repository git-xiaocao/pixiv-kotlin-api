package api.moe.xiaocao.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import api.moe.xiaocao.model.Tag

@Serializable
data class BookmarkDetailResult(
    @SerialName("is_bookmarked")
    val isBookmarked: Boolean,
    val tags: List<Tag>,
    val restrict: String,
)
