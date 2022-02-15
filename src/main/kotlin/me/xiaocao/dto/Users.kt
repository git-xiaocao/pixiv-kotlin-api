package me.xiaocao.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.xiaocao.IPixivListData
import me.xiaocao.entity.UserPreview

@Serializable
data class Users(
    @SerialName("user_previews")
    val userPreviews: List<UserPreview>,
    @SerialName("next_url")
    override val nextUrl: String? = null,
) : IPixivListData