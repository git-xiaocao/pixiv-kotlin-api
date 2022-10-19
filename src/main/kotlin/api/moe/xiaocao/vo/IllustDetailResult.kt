package api.moe.xiaocao.vo

import kotlinx.serialization.Serializable
import api.moe.xiaocao.model.Illust

@Serializable
data class IllustDetailResult(
    val illust: Illust,
)
