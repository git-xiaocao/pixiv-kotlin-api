package api.moe.xiaocao.vo

import kotlinx.serialization.Serializable
import api.moe.xiaocao.model.Stamp

@Serializable
data class StampListResult(
    val stamps: List<Stamp>
)
