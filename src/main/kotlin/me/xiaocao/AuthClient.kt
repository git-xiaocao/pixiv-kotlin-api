package me.xiaocao

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import me.xiaocao.dto.UserAccount
import java.security.SecureRandom
import javax.net.ssl.SSLContext

class AuthClient() {

    private companion object {
        const val TARGET_IP = "210.140.131.199"
        const val TARGET_HOST = "oauth.secure.pixiv.net"
        const val BASE_URL = "https://$TARGET_IP"
        const val CLIENT_ID = "MOBrBDS8blbauoSck0ZfDbtuzpyT"
        const val CLIENT_SECRET = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"
    }

    private val httpClient: HttpClient = HttpClient(OkHttp) {
        engine {
            config {
                hostnameVerifier { _, _ -> true }
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, arrayOf(CustomX509TrustManager()), SecureRandom())
                sslSocketFactory(sslContext.socketFactory, CustomX509TrustManager())

            }
        }


        defaultRequest {
            header("Host", TARGET_HOST)
            header("User-Agent", "PixivAndroidApp/6.21.0 (Android 10.0.0; XiaoCao)")
            header("App-OS", "android")
            header("App-OS-Version", "10.0.0")
            header("App-Version", "6.21.1")
            header("Accept-Language", "zh-CN")
        }

        install(ContentNegotiation) {
            json(Json {
                //指定是否应漂亮地打印结果 JSON
                prettyPrint = true
                //宽松模式
                isLenient = true
                //指定是否应编码 Kotlin 属性的默认值
                encodeDefaults = true
                //是否应忽略输入 JSON 中遇到的未知属性
                ignoreUnknownKeys = true
                //启用将不正确的 JSON 值强制为默认属性值
                coerceInputValues = true
                //指定 Json 实例是否使用 JsonNames 注释。
                useAlternativeNames = false
            })
        }

    }

    suspend fun refreshAuthToken(refreshToken: String): UserAccount {
        return httpClient.post {
            url("$BASE_URL/auth/token")

            parameter("client_id", CLIENT_ID)
            parameter("client_secret", CLIENT_SECRET)
            parameter("include_policy", true)
            parameter("grant_type", "refresh_token")
            parameter("refresh_token", refreshToken)
        }.body()

    }

    suspend fun initAccountAuthToken(code: String, codeVerifier: String): UserAccount {
        return httpClient.post {
            url("$BASE_URL/auth/token")

            parameter("client_id", CLIENT_ID)
            parameter("client_secret", CLIENT_SECRET)
            parameter("include_policy", true)
            parameter("grant_type", "authorization_code")
            parameter("code_verifier", codeVerifier)
            parameter("code", code)
            parameter("redirect_uri", "https://app-api.pixiv.net/web/v1/users/auth/pixiv/callback")
        }.body()
    }


}