package api.moe.xiaocao.vo

import kotlinx.serialization.Serializable
import api.moe.xiaocao.model.Tag

@Serializable
data class SearchAutocompleteResult(
    val tags:List<Tag>
)
