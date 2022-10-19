package api.moe.xiaocao.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * P站自己的表情包图片
 * */
@Serializable
data class Stamp(
    @SerialName("stamp_id")
    val stampId: Int,
    @SerialName("stamp_url")
    val stampUrl: String,
)
