package me.xiaocao.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.xiaocao.IPixivListData

@Serializable
data class Comments(
    val comments: List<Comments>,
    @SerialName("next_url")
    override val nextUrl: String? = null,
) : IPixivListData