package me.xiaocao.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.xiaocao.entity.Illust

/**
 * 热门标签
 * */

@Serializable
data class TrendingTags(
    @SerialName("trend_tags")
    val trendTags: List<TrendTag>,
) {
    @Serializable
    data class TrendTag(
        val tag: String,
        @SerialName("translated_name")
        val translatedName: String? = null,
        val illust: Illust,
    )
}