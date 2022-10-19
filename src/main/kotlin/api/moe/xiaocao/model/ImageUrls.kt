package api.moe.xiaocao.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageUrls(
    @SerialName("square_medium")
    val squareMedium: String,
    val medium: String,
    val large: String,
    val original: String? = null,
)
