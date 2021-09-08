package top.xiaocao.api.model

import kotlinx.serialization.Serializable
import top.xiaocao.api.entity.Illust

@Serializable
data class IllustDetail(
    val illust: Illust,
)