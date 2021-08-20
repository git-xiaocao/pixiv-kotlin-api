package top.xiaocao.api.module

import kotlinx.serialization.Serializable
import top.xiaocao.api.entity.Stamp

@Serializable
data class Stamps(
    val stamps: List<Stamp>
)