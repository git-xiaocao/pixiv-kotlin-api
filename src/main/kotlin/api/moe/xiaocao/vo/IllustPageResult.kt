package api.moe.xiaocao.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import api.moe.xiaocao.IPageList
import api.moe.xiaocao.model.Illust

@Serializable
data class IllustPageResult(
    val illusts: List<Illust>,
    @SerialName("next_url")
    override val nextUrl: String? = null,
) : IPageList
