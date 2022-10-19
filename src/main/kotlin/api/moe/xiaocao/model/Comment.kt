package api.moe.xiaocao.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Comment(
    val id: Int,
    /**
     * 内容
     * */
    val comment: String,
    val date: String,
    val user: User,
    @SerialName("has_replies")
    val hasReplies: Boolean,
    val stamp: Stamp? = null,
)
