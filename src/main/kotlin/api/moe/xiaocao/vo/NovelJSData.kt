package api.moe.xiaocao.vo

import kotlinx.serialization.Serializable

@Serializable
data class NovelJSData(
    //String
    val id: Int,
    //String
    val seriesId: Int,
    val userId: Int,
    val coverUrl: String,
    val text: String,
    val seriesNavigation: SeriesNavigation?,
) {
    @Serializable
    data class SeriesNavigation(
        val nextNovel: TurnPageNovelData?,
        val prevNovel: TurnPageNovelData?,
    ) {
        @Serializable
        data class TurnPageNovelData(
            val id: Int,
            val viewable: Boolean,
            val contentOrder: Int,
            val title: String,
            val coverUrl: String,
        )
    }
}
