package me.xiaocao.dto

import kotlinx.serialization.Serializable
import me.xiaocao.entity.Illust

@Serializable
data class IllustDetail(
    val illust: Illust,
)