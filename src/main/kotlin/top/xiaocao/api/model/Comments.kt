package top.xiaocao.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Comments(
    val comments: List<Comments>,
    @SerialName("next_url")
    val nextUrl: String? = null,
)