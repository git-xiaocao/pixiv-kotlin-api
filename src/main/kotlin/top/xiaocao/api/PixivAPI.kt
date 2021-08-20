package top.xiaocao.api

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import top.xiaocao.api.entity.Comment
import top.xiaocao.api.module.*


class CustomX509TrustManager : X509TrustManager {
    override fun getAcceptedIssuers(): Array<X509Certificate?> = arrayOf()

    override fun checkClientTrusted(certs: Array<X509Certificate?>?, authType: String?) {}

    override fun checkServerTrusted(certs: Array<X509Certificate?>?, authType: String?) {}
}

class PixivAPI {

    //210.140.131.187
    //210.140.131.188
    //210.140.131.189
    //随便用一个
    private val baseUrl = "https://210.140.131.187"


    val httpClient: HttpClient = HttpClient(OkHttp) {
        engine {
            config {

                //忽略SSL证书(X509)错误
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, arrayOf(CustomX509TrustManager()), SecureRandom())
                sslSocketFactory(sslContext.socketFactory, CustomX509TrustManager())
                //忽略域名校验
                hostnameVerifier { _, _ -> true }
            }
        }


        defaultRequest {

            //指定Host 不然请求会失败(服务器不知道请求的是那个域名)
            header("Host", "app-api.pixiv.net")

            header("User-Agent", "PixivAndroidApp/6.19.0 (Android 7.1.2; XiaoCao)")
            header("App-OS", "android")
            header("App-OS-Version", "7.1.2")
            header("App-Version", "6.1.0")
            header("Accept-Language", "zh-CN")

            //抓包 或者 登录OAuth 获取  还没写
            header("Authorization", "Bearer 这里填Token")


        }


        install(HttpTimeout) {
            requestTimeoutMillis = 6000
            connectTimeoutMillis = 6000
            socketTimeoutMillis = 6000
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(

                json = Json {
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


    suspend inline fun <reified T> next(url: String): T {

        return httpClient.get(url.replaceFirst("app-api.pixiv.net", "210.140.131.187"))
    }


    /**
     *  @param userId 用户ID
     *  @param restrict 为ture获取公开的(public) 反之不公开(private)
     * */
    suspend fun getUserBookmarks(userId: Int, restrict: Boolean = true): Illusts {
        return httpClient.get {
            url("${baseUrl}/v1/user/bookmarks/illust")
            parameter("user_id", userId)
            parameter("filter", "for_android")

            parameter("restrict", if (restrict) "public" else "private")

        }
    }

    /**
     * 获取用户的插画
     *  @param userId 用户ID
     *  @param type 类型
     * */
    suspend fun getUserIllusts(userId: Int, type: WorkType): Illusts {

        return httpClient.get {
            url("${baseUrl}/v1/user/illusts")
            parameter("filter", "for_android")
            parameter("user_id", userId)
            parameter("type", type.value)
        }
    }

    /**
     * 获取推荐作品
     *  @param type 类型
     * */
    suspend fun getRecommendedIllusts(type: WorkType): Illusts {

        return httpClient.get {
            url("${baseUrl}/v1/${type.value}/recommended")

            parameter("filter", "for_android")
        }
    }

    /**
     * 获取排行榜
     *  @param mode 类型
     * */
    suspend fun getRanking(mode: RankingMode): Illusts {

        return httpClient.get {
            url("${baseUrl}/v1/illust/ranking")

            parameter("filter", "for_android")
            parameter("mode", mode.value)
        }
    }

    /**
     * 获取推荐标签(搜索用的)
     * */
    suspend fun getTrendingTags(): TrendingTags {
        return httpClient.get {
            url("${baseUrl}/v1/trending-tags/illust")

            parameter("filter", "for_android")
        }
    }

    /**
     * 获取推荐用户
     * */
    suspend fun getRecommendedUsers(): Users {
        return httpClient.get {
            url("${baseUrl}/v1/user/recommended")

            parameter("filter", "for_android")
        }
    }

    /**
     *  @param userId 用户ID
     *  @param restrict 为ture获取公开的(public) 反之不公开(private)
     * */
    suspend fun getFollowingUsers(userId: Int, restrict: Boolean = true): Users {
        return httpClient.get {
            url("${baseUrl}/v1/user/following")

            parameter("user_id", userId)
            parameter("restrict", if (restrict) "public" else "private")

            parameter("filter", "for_android")
        }
    }

    /**
     * 获取已关注用户的最新插画
     *  @param restrict 为null获取全部(all) 为ture获取公开的(public) 反之不公开(private)
     * */
    suspend fun getFollowingLatestIllust(restrict: Boolean? = null): Illusts {
        return httpClient.get {
            url("${baseUrl}/v1/illust/follow")

            parameter("filter", "for_android")
            if (null != restrict) {
                parameter("restrict", if (restrict) "public" else "private")
            } else {
                parameter("restrict", "all")
            }

        }
    }

    /**
     * 获取最近发布的插画
     *  @param type 作品类型
     * */
    suspend fun getNewIllusts(type: WorkType): Illusts {
        return httpClient.get {
            url("${baseUrl}/v1/illust/new")

            parameter("filter", "for_android")
            parameter("content_type", type.value)
        }
    }

    /**
     * 获取插画的相关推荐
     * @param illustId 插画ID
     * */
    suspend fun getIllustRelated(illustId: Int): Illusts {
        return httpClient.get {
            url("${baseUrl}/v2/illust/related")

            parameter("filter", "for_android")
            parameter("illust_id", illustId)
        }
    }

    /**
     * 获取评论的回复
     * @param commentId 插画ID
     * */
    suspend fun getCommentReplies(commentId: Int): Comments {
        return httpClient.get {
            url("${baseUrl}/v2/illust/comment/replies")

            parameter("comment_id", commentId)
        }
    }

    /**
     * 获取插画的评论
     * @param illustId 插画ID
     * */
    suspend fun getIllustComments(illustId: Int): Comments {
        return httpClient.get {
            url("${baseUrl}/v3/illust/comments")

            parameter("illust_id", illustId)
        }
    }

    /**
     * 添加评论(评论一个插画)
     * @param illustId 插画ID
     * @param comment 评论内容
     * @param parentCommentId 父评论ID(用来回复)
     * */
    suspend fun addComment(illustId: Int, comment: String, parentCommentId: Int? = null): Comment {
        return httpClient.post {
            url("${baseUrl}/v1/illust/comment/add")

            parameter("illust_id", illustId)
            parameter("comment", comment)
            if (null != parentCommentId) {
                parameter("parent_comment_id", parentCommentId)
            }
        }
    }

    /**
     * 添加评论(发一个P站自带的表情包)
     * @param illustId 插画ID
     * @param stampId 表情包ID
     * */
    suspend fun addComment(illustId: Int, stampId: Int): Comment {
        return httpClient.post {
            url("${baseUrl}/v1/illust/comment/add")

            parameter("illust_id", illustId)
            parameter("comment", "")
            parameter("stamp_id", stampId)
        }
    }

    /**
     * 删除评论(自己的)
     * @param commentId 评论ID
     * */
    suspend fun deleteComment(commentId: Int) {
        return httpClient.post {
            url("${baseUrl}/v1/illust/comment/delete")


            parameter("comment_id", commentId)
        }
    }

    /**
     * **搜索**的**关键字**自动补全
     * @param word 关键字
     * */
    suspend fun searchAutocomplete(word: String): SearchAutocomplete {
        return httpClient.get {
            url("${baseUrl}/v2/search/autocomplete")

            parameter("merge_plain_keyword_results", true)
            parameter("word", word)

        }
    }

    /**
     * 搜索
     * @param word 关键字
     * @param sort 排序
     * @param target 搜索目标
     * @param startDate 开始时间(必须跟[endDate]一起填)
     * @param endDate 结束时间(必须跟[startDate]一起填)
     * */
    suspend fun search(
        word: String, sort: SearchSort,
        target: SearchTarget,
        startDate: String? = null,
        endDate: String? = null
    ): Search {
        return httpClient.get {
            url("${baseUrl}/v1/search/illust")

            parameter("filter", "for_android")
            parameter("include_translated_tag_results", true)
            parameter("merge_plain_keyword_results", true)
            parameter("word", word)
            parameter("sort", sort.value)
            parameter("search_target", target.value)

            if (null != startDate && null != endDate) {
                parameter("start-date", startDate)
                parameter("end-date", endDate)
            }


        }
    }

    /**
     * 收藏插画
     * @param illustId 插画ID
     * @param restrict 为ture获取公开的(public) 反之不公开(private)
     * */
    suspend fun bookmarkAdd(illustId: Int, restrict: Boolean = true) {
        return httpClient.post {
            url("${baseUrl}/v2/illust/bookmark/add")
            parameter("illust_id", illustId)
            parameter("restrict", if (restrict) "public" else "private")

        }
    }

    /**
     * 取消收藏插画
     * @param illustId 插画ID
     * */
    suspend fun bookmarkDelete(illustId: Int) {
        return httpClient.post {
            url("${baseUrl}/v1/illust/bookmark/delete")
            parameter("illust_id", illustId)
        }
    }

    /**
     * 关注用户
     * @param userId 用户ID
     * @param restrict 为ture获取公开的(public) 反之不公开(private)
     * */
    suspend fun followAdd(userId: Int, restrict: Boolean = true) {
        return httpClient.post {
            url("${baseUrl}/v1/user/follow/add")
            parameter("user_id", userId)
            parameter("restrict", if (restrict) "public" else "private")

        }
    }

    /**
     * 取消关注用户
     * @param userId 用户ID
     * */
    suspend fun followDelete(userId: Int) {
        return httpClient.post {
            url("${baseUrl}/v1/user/follow/delete")
            parameter("user_id", userId)

        }
    }


}