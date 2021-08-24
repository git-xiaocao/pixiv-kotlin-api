package top.xiaocao.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorMessage(
    val error: Error,
)

@Serializable
data class Error(
    @SerialName("user_message")
    val userMessage: String?,
    val message: String?,

//    @SerialName("user_message_details")
//    val userMessageDetails: Any,
)