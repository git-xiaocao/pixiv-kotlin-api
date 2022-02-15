package me.xiaocao.dto

import kotlinx.serialization.Serializable
import me.xiaocao.entity.Stamp

@Serializable
data class Stamps(
    val stamps: List<Stamp>
)