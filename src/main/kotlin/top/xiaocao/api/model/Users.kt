package top.xiaocao.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import top.xiaocao.api.entity.UserPreview

@Serializable
data class Users(
    @SerialName("user_previews")
    val userPreviews: List<UserPreview>,
    @SerialName("next_url")
    val nextUrl: String?,
)