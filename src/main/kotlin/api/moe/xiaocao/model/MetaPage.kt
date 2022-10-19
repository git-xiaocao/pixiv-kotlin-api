package api.moe.xiaocao.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaPage(
    @SerialName("image_urls")
    val imageUrls: ImageUrls,
)
