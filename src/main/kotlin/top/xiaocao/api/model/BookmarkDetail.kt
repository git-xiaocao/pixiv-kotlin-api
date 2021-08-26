package top.xiaocao.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import top.xiaocao.api.entity.Tag

@Serializable
data class BookmarkDetail(
    @SerialName("is_bookmarked")
    val isBookmarked: Boolean,
    val tags: List<Tag>,
    val restrict: String,
)