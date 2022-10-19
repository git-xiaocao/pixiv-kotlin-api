package api.moe.xiaocao.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val account: String,
    @SerialName("profile_image_urls")
    val profileImageUrls: ProfileImageUrls,
    /**
     * 当[User]在Comment中的时候没有这个字段
     * */
    @SerialName("is_followed")
    val isFollowed: Boolean?,
)
