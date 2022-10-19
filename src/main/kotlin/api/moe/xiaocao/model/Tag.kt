package api.moe.xiaocao.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val name: String,
    @SerialName("translated_name")
    val translatedName: String? = null,
)
