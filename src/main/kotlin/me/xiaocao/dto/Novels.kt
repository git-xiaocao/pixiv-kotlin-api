package me.xiaocao.dto

import kotlinx.serialization.Serializable
import me.xiaocao.IPixivListData
import me.xiaocao.entity.Novel

@Serializable
data class Novels(
    val novels: List<Novel>,
    override val nextUrl: String,
) : IPixivListData
