package api.moe.xiaocao.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPreview(
    val user: User,
    val illusts: List<Illust>,
    //novels : List<Novel> 小说
    @SerialName("is_muted")
    val isMuted:Boolean,

)
