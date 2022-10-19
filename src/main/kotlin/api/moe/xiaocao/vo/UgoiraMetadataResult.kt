package api.moe.xiaocao.vo

import kotlinx.serialization.Serializable

@Serializable
data class UgoiraMetadataResult(
    val ugoiraMetadata: Content,
) {
    @Serializable
    data class Content(
        val zipUrls: ZipUrls,
        val frames: List<Frame>,
    ) {

        @Serializable
        data class ZipUrls(
            val medium: String,
        )

        @Serializable
        data class Frame(
            val file: String,
            val delay: Int,
        )
    }
}
