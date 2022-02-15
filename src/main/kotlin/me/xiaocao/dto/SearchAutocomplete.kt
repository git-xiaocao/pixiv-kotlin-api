package me.xiaocao.dto

import kotlinx.serialization.Serializable
import me.xiaocao.entity.Tag

@Serializable
data class SearchAutocomplete(
    val tags:List<Tag>
)
