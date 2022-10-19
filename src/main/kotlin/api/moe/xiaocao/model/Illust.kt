package api.moe.xiaocao.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Illust(
    val id: Int,
    val title: String,
    val type: String,
    @SerialName("image_urls")
    val imageUrls: ImageUrls,
    val caption: String,
    val restrict: Int,
    val user: User,
    val tags: List<Tag>,
    //画师制作这个作品使用的工具 如 "PS" "SAI" "Live2D" 等
    val tools: List<String>,
    @SerialName("create_date")
    val createDate: String,
    @SerialName("page_count")
    val pageCount: Int,
    val width: Int,
    val height: Int,
    @SerialName("sanity_level")
    val sanityLevel: Int,
    @SerialName("x_restrict")
    val xRestrict: Int,
    //series :null
    /**
     * 当[pageCount]等于1时 图片的originalImageUrl存在这里
     * */
    @SerialName("meta_single_page")
    val metaSinglePage: MetaSinglePage,
    /**
     * 当[pageCount]大于1时 图片的[ImageUrls]存在这里
     * */
    @SerialName("meta_pages")
    val metaPages: List<MetaPage>,
    @SerialName("total_view")
    val totalView: Int,
    @SerialName("total_bookmarks")
    val totalBookmarks: Int,
    @SerialName("is_bookmarked")
    val isBookmarked: Boolean,
    val visible: Boolean,
    @SerialName("is_muted")
    val isMuted: Boolean,
    @SerialName("total_comments")
    val totalComments: Int? = null,
)
