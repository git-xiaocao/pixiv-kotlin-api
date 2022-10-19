package api.moe.xiaocao.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import api.moe.xiaocao.IPageList

@Serializable
data class CommentPageResult(
    val comments: List<CommentPageResult>,
    @SerialName("next_url")
    override val nextUrl: String? = null,
) : IPageList
