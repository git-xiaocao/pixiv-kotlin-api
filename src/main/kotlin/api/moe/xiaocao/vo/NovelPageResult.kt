package api.moe.xiaocao.vo

import kotlinx.serialization.Serializable
import api.moe.xiaocao.IPageList
import api.moe.xiaocao.model.Novel

@Serializable
data class NovelPageResult(
    val novels: List<Novel>,
    override val nextUrl: String,
) : IPageList
