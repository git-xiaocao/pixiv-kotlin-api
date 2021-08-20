package top.xiaocao.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val account: String,
    @SerialName("profile_image_urls")
    val profileImageUrls: ProfileImageUrls,
    @SerialName("is_followed")
    val isFollowed: Boolean,
)
