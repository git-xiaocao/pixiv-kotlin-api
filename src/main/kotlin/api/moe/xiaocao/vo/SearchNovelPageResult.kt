package api.moe.xiaocao.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import api.moe.xiaocao.IPageList
import api.moe.xiaocao.model.Novel

@Serializable
data class SearchNovelPageResult(
    val novels: List<Novel>,
    @SerialName("search_span_limit")
    val searchSpanLimit: Int,
    @SerialName("next_url")
    override val nextUrl: String? = null,
) : IPageList
