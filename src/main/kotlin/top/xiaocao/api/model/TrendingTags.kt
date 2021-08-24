package top.xiaocao.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import top.xiaocao.api.entity.Illust

/**
 * 热门标签
 * */

@Serializable
data class TrendingTags(
    @SerialName("trend_tags")
    val trendTags: List<TrendTag>,
)

@Serializable
data class TrendTag(
    val tag: String,
    @SerialName("translated_name")
    val translatedName: String? = null,
    val illust: Illust,
)