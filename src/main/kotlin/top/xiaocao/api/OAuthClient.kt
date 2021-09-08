package top.xiaocao.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import top.xiaocao.api.model.UserAccount
import java.security.SecureRandom
import javax.net.ssl.SSLContext

class OAuthClient {
    private val targetIP = "210.140.131.199"
    private val targetHost = "oauth.secure.pixiv.net"
    private val baseUrl = "https://${targetIP}"

    private  val clientId ="MOBrBDS8blbauoSck0ZfDbtuzpyT"
    private  val clientSecret ="lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"

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

            //指定Host 不然请求会失败(服务器不知道请求的是那个域名)
            header("Host", targetHost)

            header("User-Agent", "PixivAndroidApp/6.21.0 (Android 7.1.2; XiaoCao)")

            parameter("client_id", clientId)
            parameter("client_secret", clientSecret)
            parameter("include_policy", true)
        }


        install(HttpTimeout) {
            requestTimeoutMillis = 6000
            connectTimeoutMillis = 6000
            socketTimeoutMillis = 6000
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(

                json = kotlinx.serialization.json.Json {
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
                }
            )
        }

    }

    suspend fun refreshAuthToken(refreshToken: String): UserAccount {
        return httpClient.post {
            url("${baseUrl}/auth/token")
            parameter("grant_type", "refresh_token")
            parameter("refresh_token", refreshToken)

        }
    }

    suspend fun initAccountAuthToken(code: String, codeVerifier: String): UserAccount {
        return httpClient.post {
            url("${baseUrl}/auth/token")
            parameter("grant_type", "authorization_code")
            parameter("code_verifier", codeVerifier)
            parameter("code", code)
            parameter("redirect_uri", "https://app-api.pixiv.net/web/v1/users/auth/pixiv/callback")
        }
    }


}