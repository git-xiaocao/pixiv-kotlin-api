package api.moe.xiaocao.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import api.moe.xiaocao.model.Illust

/**
 * 热门标签
 * */

@Serializable
data class TrendingTagListResult(
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
