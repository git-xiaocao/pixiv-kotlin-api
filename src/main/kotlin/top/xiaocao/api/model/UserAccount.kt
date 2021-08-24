package top.xiaocao.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import top.xiaocao.api.entity.LocalUser

@Serializable
data class UserAccount(
    @SerialName("access_token")
    val accessToken:String,
    @SerialName("expires_in")
    val expiresIn:String,
    @SerialName("token_type")
    val tokenType:String,
    val scope:String,
    @SerialName("refresh_token")
    val refreshToken:String,
    val user:LocalUser,
)