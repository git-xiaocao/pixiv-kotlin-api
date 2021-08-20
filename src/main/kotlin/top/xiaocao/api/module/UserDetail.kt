package top.xiaocao.api.module

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import top.xiaocao.api.entity.ProfileImageUrls

@Serializable
data class UserDetail(
    val user: UserInfo,
    val profile: UserProfile,
    @SerialName("profile_publicity")
    val profilePublicity: UserProfilePublicity,
    val workspace: UserWorkspace,
)

@Serializable
data class UserInfo(
    val id: Int,
    val name: String,
    val account: String,
    @SerialName("profile_image_urls")
    val profileImageUrls: ProfileImageUrls,
    val comment: String? = null,
    @SerialName("is_followed")
    val isFollowed: Boolean,
)

@Serializable
data class UserProfile(
    val webpage: String? = null,
    val gender: String,
    //出生
    val birth: String,
    @SerialName("birth_day")
    val birth_day: String,
    @SerialName("birth_year")
    val birth_year: Int,
    val region: String,
    @SerialName("address_id")
    val address_id: Int,
    @SerialName("country_code")
    val country_code: Int,
    val job: String,
    @SerialName("job_id")
    val job_id: Int,
    @SerialName("total_follow_users")
    val total_follow_users: Int,
    @SerialName("total_mypixiv_users")
    val total_mypixiv_users: Int,
    @SerialName("total_illusts")
    val total_illusts: Int,
    @SerialName("total_manga")
    val total_manga: Int,
    @SerialName("total_novels")
    val total_novels: Int,
    @SerialName("total_illust_bookmarks_public")
    val total_illust_bookmarks_public: Int,
    @SerialName("total_illust_series")
    val total_illust_series: Int,
    @SerialName("total_novel_series")
    val total_novel_series: Int,
    @SerialName("background_image_url")
    val background_image_url: String? = null,
    @SerialName("twitter_account")
    val twitter_account: String? = null,
    @SerialName("twitter_url")
    val twitter_url: String? = null,
    @SerialName("pawoo_url")
    val pawoo_url: String? = null,
    @SerialName("is_premium")
    val is_premium: Boolean,
    @SerialName("is_using_custom_profile_image")
    val is_using_custom_profile_image: Boolean,
)

@Serializable
data class UserProfilePublicity(
    val gender: String,
    val region: String,
    @SerialName("birth_day")
    val birthDay: String,
    @SerialName("birth_year")
    val birthYear: String,
    val job: String,
    val pawoo: Boolean,
)

@Serializable
data class UserWorkspace(
    /**
     * 电脑
     * */
    val pc: String,
    /**
     * 显示器
     * */
    val monitor: String,
    /**
     * 软件
     * */
    val tool: String,
    /**
     * 扫描仪
     * */
    val scanner: String,
    /**
     * 数位板
     * */
    val tablet: String,
    /**
     * 鼠标
     * */
    val mouse: String,
    /**
     * 打印机
     * */
    val printer: String,
    /**
     * 桌子上的东西
     * */
    val desktop: String,
    /**
     * 画图时听的音乐
     * */
    val music: String,
    /**
     * 桌子
     * */
    val desk: String,
    /**
     * 椅子
     * */
    val chair: String,
    /**
     * 其他问题
     * */
    val comment: String,
    @SerialName("workspace_image_url")
    val workspaceImageUrl: String? = null,
)