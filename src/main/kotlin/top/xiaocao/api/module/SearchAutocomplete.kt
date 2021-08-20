package top.xiaocao.api.module

import kotlinx.serialization.Serializable
import top.xiaocao.api.entity.Tag

@Serializable
data class SearchAutocomplete(
    val tags:List<Tag>
)
