package me.xiaocao.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.xiaocao.entity.LocalUser

@Serializable
data class UserAccount(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: String,
    @SerialName("token_type")
    val tokenType: String,
    val scope: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    val user: LocalUser,
)