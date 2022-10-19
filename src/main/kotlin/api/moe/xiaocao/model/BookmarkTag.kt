package api.moe.xiaocao.model

import kotlinx.serialization.Serializable

@Serializable
data class BookmarkTag(
    val name: String,
    val title: String,
)
