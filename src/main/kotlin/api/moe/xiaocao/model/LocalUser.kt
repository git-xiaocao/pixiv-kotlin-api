package api.moe.xiaocao.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocalUser(
    @SerialName("profile_image_urls")
    val profileImageUrls: LocalUserProfileImageUrls,
    val id: String,
    val name: String,
    val account: String,
    @SerialName("mail_address")
    val mailAddress: String,
    @SerialName("is_premium")
    val isPremium: Boolean,
    @SerialName("x_restrict")
    val xRestrict: Int,
    @SerialName("is_mail_authorized")
    val isMailAuthorized: Boolean,
    @SerialName("require_policy_agreement")
    val requirePolicyAgreement: Boolean,
)

@Serializable
data class LocalUserProfileImageUrls(
    @SerialName("px_16x16")
    val px16x16: String,
    @SerialName("px_50x50")
    val px50x50: String,
    @SerialName("px_170x170")
    val px170x170: String,
)
