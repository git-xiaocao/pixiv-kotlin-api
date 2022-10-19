package api.moe.xiaocao.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Novel(
    val id: Int,
    val title: String,
    val caption: String,
    val restrict: Int,
    @SerialName("x_restrict")
    val xRestrict: Int,
    @SerialName("is_original")
    val isOriginal: Boolean,
    @SerialName("image_urls")
    val imageUrls: ImageUrls,
    @SerialName("create_date")
    val createDate: String,
    val tags: List<Tag>,
    @SerialName("page_count")
    val pageCount: Int,
    @SerialName("text_length")
    val textLength: Int,
    val user: User,
    val series: Series,
    @SerialName("total_bookmarks")
    val totalBookmarks:Int,
    @SerialName("is_bookmarked")
    val isBookmarked:Boolean,
    @SerialName("total_view")
    val totalView:Int,
    val visible:Boolean,
    @SerialName("total_comments")
    val totalComments:Int,
    @SerialName("is_muted")
    val isMuted:Boolean,
    @SerialName("is_mypixiv_only")
    val isMyPixivOnly:Boolean,
    @SerialName("is_x_restricted")
    val isXRestricted:Boolean,

) {
    @Serializable
    data class Series(
        val id: Int?,
        val title: String?,
    )
}
