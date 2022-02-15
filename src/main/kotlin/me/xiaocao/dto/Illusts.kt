package me.xiaocao.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.xiaocao.IPixivListData
import me.xiaocao.entity.Illust

@Serializable
data class Illusts(
    val illusts: List<Illust>,
    @SerialName("next_url")
    override val nextUrl: String? = null,
) : IPixivListData
