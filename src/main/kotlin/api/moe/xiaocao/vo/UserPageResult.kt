package api.moe.xiaocao.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import api.moe.xiaocao.IPageList
import api.moe.xiaocao.model.UserPreview

@Serializable
data class UserPageResult(
    @SerialName("user_previews")
    val userPreviews: List<UserPreview>,
    @SerialName("next_url")
    override val nextUrl: String? = null,
) : IPageList
