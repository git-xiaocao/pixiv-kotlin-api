package top.xiaocao.api.module

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import top.xiaocao.api.entity.Illust

@Serializable
data class Search(
    val illusts: List<Illust>,
    @SerialName("next_url")
    val nextUrl: String? = null,
    @SerialName("search_span_limit")
    val searchSpanLimit: Int,
)
