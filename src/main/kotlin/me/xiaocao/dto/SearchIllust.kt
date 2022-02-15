package me.xiaocao.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.xiaocao.IPixivListData
import me.xiaocao.entity.Illust

@Serializable
data class SearchIllust(
    val illusts: List<Illust>,
    @SerialName("search_span_limit")
    val searchSpanLimit: Int,
    @SerialName("next_url")
    override val nextUrl: String? = null,
) : IPixivListData
