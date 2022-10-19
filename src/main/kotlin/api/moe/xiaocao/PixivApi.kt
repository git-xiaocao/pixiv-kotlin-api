package api.moe.xiaocao

import java.security.SecureRandom
import javax.net.ssl.SSLContext

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import api.moe.xiaocao.vo.*
import api.moe.xiaocao.model.Comment
import okhttp3.Interceptor
import okhttp3.Response


@SuppressWarnings("unused")
class PixivApi(private var userAccount: UserAccount, private val updateUserAccount: (data: UserAccount) -> Unit) {
    private companion object {
        const val TARGET_IP = "210.140.131.199"
        const val TARGET_HOST = "app-api.pixiv.net"
        const val BASE_URL = "https://${TARGET_IP}"
    }

    private val auth = PixivAuth()

    private fun interceptor(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(
            request.newBuilder().apply {
                header("Authorization", "Bearer ${userAccount.accessToken}")
            }.build()
        )

        if (HttpStatusCode.BadRequest.value == response.code) {
            if (true == response.body?.byteString()?.utf8()?.contains("OAuth")) {
                return chain.proceed(
                    request.newBuilder().apply {
                        runBlocking {
                            userAccount = auth.refreshAuthToken(userAccount.refreshToken).also {
                                updateUserAccount(it)
                                header("Authorization", it.accessToken)
                            }
                        }
                    }.build()
                )
            }
        }
        return response
    }

    private val httpClient: HttpClient = HttpClient(OkHttp) {
        engine {
            config {
                //忽略SSL证书(X509)错误
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, arrayOf(CustomX509TrustManager()), SecureRandom())
                sslSocketFactory(sslContext.socketFactory, CustomX509TrustManager())
                //忽略域名校验
                hostnameVerifier { _, _ -> true }
                addInterceptor {
                    interceptor(it)
                }
            }
        }

        defaultRequest {
            header("Host", TARGET_HOST)
            header("User-Agent", "PixivAndroidApp/6.54.0 (Android 11.0; XiaoCao)")
            header("App-OS", "android")
            header("App-OS-Version", "11.0")
            header("App-Version", "6.54.0")
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

    suspend inline fun <reified T : IPageList> next(url: String): T {
        return nextResponse(url).body()
    }

    suspend fun nextResponse(url: String): HttpResponse {
        return httpClient.get(
            url.replaceFirst(
                TARGET_HOST,
                TARGET_IP
            )
        )
    }

    /**
     * 获取用户详细信息
     * @param userId 用户ID
     * */
    suspend fun getUserDetail(userId: Int): UserDetailResult {
        return httpClient.get {
            url("${BASE_URL}/v1/user/detail")
            parameter("filter", "for_android")
            parameter("user_id", userId)
        }.body()
    }

    /**
     * 获取用户收藏的插画
     *  @param userId 用户ID
     *  @param restrict
     * */
    suspend fun getUserIllustBookmarkPage(userId: Int, restrict: Restrict): IllustPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/user/bookmarks/illust")
            parameter("user_id", userId)
            parameter("restrict", restrict.value)
        }.body()
    }

    /**
     * 获取用户收藏的小说
     *  @param userId 用户ID
     *  @param restrict
     * */
    suspend fun getUserNovelBookmarkPage(userId: Int, restrict: Restrict): IllustPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/user/bookmarks/novel")
            parameter("user_id", userId)
            parameter("restrict", restrict.value)
        }.body()
    }

    /**
     * 获取用户的插画
     *  @param userId 用户ID
     *  @param type 类型
     * */
    suspend fun getUserIllustPage(userId: Int, type: IllustType): IllustPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/user/illusts")
            parameter("filter", "for_android")
            parameter("user_id", userId)
            parameter("type", type.value)
        }.body()
    }

    /**
     * 获取用户的小说
     *  @param userId 用户ID
     * */
    suspend fun getUserNovelPage(userId: Int): NovelPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/user/novels")
            parameter("user_id", userId)
        }.body()
    }

    /**
     * 获取推荐插画
     *  @param type 类型
     * */
    suspend fun getRecommendedIllustPage(type: IllustType): IllustPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/${type.value}/recommended")
            parameter("filter", "for_android")
        }.body()
    }

    /**
     * 获取推荐小说
     * */
    suspend fun getRecommendedNovelPage(): NovelPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/novel/recommended")
            parameter("include_ranking_novels", false)
            parameter("include_ranking_novels", false)
        }.body()
    }

    /**
     * 获取排行榜
     *  @param mode 类型
     * */
    suspend fun getRankingPage(mode: RankingMode): IllustPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/illust/ranking")
            parameter("filter", "for_android")
            parameter("mode", mode.value)
        }.body()
    }

    /**
     * 获取推荐标签(搜索用的)
     * */
    suspend fun getTrendingTagList(): TrendingTagListResult {
        return httpClient.get {
            url("${BASE_URL}/v1/trending-tags/illust")
            parameter("filter", "for_android")
        }.body()
    }

    /**
     * 获取推荐用户
     * */
    suspend fun getRecommendedUserPage(): UserPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/user/recommended")
            parameter("filter", "for_android")
        }.body()
    }


    /**
     *
     * 获取粉丝
     * @param userId 用户ID
     * */
    suspend fun getFollowerPage(userId: Int): UserPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/user/follower")
            parameter("filter", "for_android")
            parameter("user_id", userId)
        }.body()
    }

    /**
     * 获取关注用户
     *  @param userId 用户ID
     *  @param restrict
     * */
    suspend fun getFollowingUserPage(userId: Int, restrict: Restrict): UserPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/user/following")
            parameter("filter", "for_android")
            parameter("user_id", userId)
            parameter("restrict", restrict.value)
        }.body()
    }

    /**
     * 获取好P友
     *  @param userId 用户ID
     * */
    suspend fun getMyPixivPage(userId: Int): UserPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/user/mypixiv")
            parameter("filter", "for_android")
            parameter("user_id", userId)
        }.body()
    }

    /**
     * 获取关注者的新插画
     *  @param restrict 为null获取全部(all)
     * */
    suspend fun getFollowNewIllustPage(restrict: Restrict? = null): IllustPageResult {
        return httpClient.get {
            url("${BASE_URL}/v2/illust/follow")
            parameter("filter", "for_android")
            parameter("restrict", restrict?.value ?: "all")
        }.body()
    }

    /**
     * 获取好P友的新插画
     * */
    suspend fun getMyPixivNewIllustPage(): IllustPageResult {
        return httpClient.get {
            url("${BASE_URL}/v2/illust/mypixiv")
            parameter("filter", "for_android")
        }.body()
    }

    /**
     * 获取关注者的新小说
     *  @param restrict 为null获取全部(all)
     * */
    suspend fun getFollowNewNovelPage(restrict: Restrict? = null): IllustPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/novel/follow")
            parameter("filter", "for_android")
            parameter("restrict", restrict?.value ?: "all")
        }.body()
    }

    /**
     * 获取好P友的新小说
     * */
    suspend fun getMyPixivNewNovelPage(): IllustPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/novel/mypixiv")
            parameter("filter", "for_android")
        }.body()
    }

    /**
     * 获取最近发布的插画
     *  @param type 作品类型
     * */
    suspend fun getNewIllustPage(type: IllustType): IllustPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/illust/new")
            parameter("filter", "for_android")
            parameter("content_type", type.value)
        }.body()
    }

    /**
     * 获取最近发布的小说
     * */
    suspend fun getNewNovelPage(): NovelPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/novel/new")
        }.body()
    }

    /**
     * 获取插画的相关推荐
     * @param illustId 插画ID
     * */
    suspend fun getIllustRelatedPage(illustId: Int): IllustPageResult {
        return httpClient.get {
            url("${BASE_URL}/v2/illust/related")
            parameter("filter", "for_android")
            parameter("illust_id", illustId)
        }.body()
    }

    /**
     * 获取插画详细
     * @param illustId 插画ID
     * */
    suspend fun getIllustDetail(illustId: Int): IllustDetailResult {
        return httpClient.get {
            url("${BASE_URL}/v1/illust/detail")
            parameter("filter", "for_android")
            parameter("illust_id", illustId)
        }.body()
    }

    /**
     * 获取小说HTML页面
     * @param novelId 小说ID
     * */
    suspend fun getNovelHtml(novelId: Int): String {
        return httpClient.get {
            url("${BASE_URL}/webview/v1/novel")
            parameter("id", novelId)
        }.bodyAsText()
    }

    /**
     * 获取动图
     * @param illustId 插画ID
     * */
    suspend fun getUgoiraMetadata(illustId: Int): UgoiraMetadataResult {
        return httpClient.get {
            url("${BASE_URL}/v1/ugoira/metadata")
            parameter("illust_id", illustId)
        }.body()
    }

    /**
     * 获取评论的回复
     * @param commentId 评论ID
     * */
    suspend fun getCommentReplyPage(commentId: Int): CommentPageResult {
        return httpClient.get {
            url("${BASE_URL}/v2/illust/comment/replies")
            parameter("comment_id", commentId)
        }.body()
    }

    /**
     * 获取插画的评论
     * @param illustId 插画ID
     * */
    suspend fun getIllustCommentPage(illustId: Int): CommentPageResult {
        return httpClient.get {
            url("${BASE_URL}/v3/illust/comments")
            parameter("illust_id", illustId)
        }.body()
    }

    /**
     * **搜索**的**关键字**自动补全z
     * @param word 关键字
     * */
    suspend fun getSearchAutocomplete(word: String): SearchAutocompleteResult {
        return httpClient.get {
            url("${BASE_URL}/v2/search/autocomplete")
            parameter("merge_plain_keyword_results", true)
            parameter("word", word)
        }.body()
    }

    /**
     * 搜索插画
     * @param word 关键字
     * @param sort 排序
     * @param target 搜索目标
     * @param startDate 开始时间(必须跟[endDate]一起填)
     * @param endDate 结束时间(必须跟[startDate]一起填)
     * @param bookmarkTotal 收藏数量 100, 250, 500, 1000, 5000, 7500 , 10000, 20000, 30000, 50000
     * */
    suspend fun getSearchIllustPage(
        word: String, sort: SearchSort,
        target: SearchIllustTarget,
        startDate: String? = null,
        endDate: String? = null,
        bookmarkTotal: Int? = null
    ): SearchIllustPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/search/illust")
            parameter("filter", "for_android")
            parameter("include_translated_tag_results", true)
            parameter("merge_plain_keyword_results", true)
            parameter("word", bookmarkTotal.let {
                if (null == it) {
                    word
                } else {
                    "$word ${it}users入り"
                }
            })
            parameter("sort", sort.value)
            parameter("search_target", target.value)
            if (null != startDate && null != endDate) {
                parameter("start_date", startDate)
                parameter("end_date", endDate)
            }
        }.body()
    }

    /**
     * 搜索小说
     * @param word 关键字
     * @param sort 排序
     * @param target 搜索目标
     * @param startDate 开始时间(必须跟[endDate]一起填)
     * @param endDate 结束时间(必须跟[startDate]一起填)
     * @param bookmarkTotal 收藏数量 100, 250, 500, 1000, 5000, 7500 , 10000, 20000, 30000, 50000
     * */
    suspend fun getSearchNovelPage(
        word: String, sort: SearchSort,
        target: SearchNovelTarget,
        startDate: String? = null,
        endDate: String? = null,
        bookmarkTotal: Int? = null,
    ): SearchNovelPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/search/novel")
            parameter("include_translated_tag_results", true)
            parameter("merge_plain_keyword_results", true)
            parameter("word", bookmarkTotal.let {
                if (null == it) {
                    word
                } else {
                    "$word ${it}users入り"
                }
            })
            parameter("sort", sort.value)
            parameter("search_target", target.value)
            if (null != startDate && null != endDate) {
                parameter("start_date", startDate)
                parameter("end_date", endDate)
            }
        }.body()
    }

    /**
     * 搜索用户
     * */
    suspend fun getSearchUserPage(word: String): UserPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/search/user")
            parameter("filter", "for_android")
            parameter("word", word)
        }.body()
    }

    /**
     * 获取收藏标签
     * @param userId 用户ID
     * @param isNovel 是否为小说
     * */
    suspend fun getBookmarkTagPage(userId: Int, restrict: Restrict, isNovel: Boolean): UserPageResult {
        return httpClient.get {
            url("${BASE_URL}/v1/user/bookmark-tags/${if (isNovel) "novel" else "illust"}")
            parameter("user_id", userId)
            parameter("restrict", restrict.value)
        }.body()
    }

    /**
     * 获取书签详细
     * @param illustId 插画ID
     * */
    suspend fun getBookmarkDetail(illustId: Int): BookmarkDetailResult {
        return httpClient.post {
            url("${BASE_URL}/v2/illust/bookmark/detail")
            parameter("illust_id", illustId)
        }.body()
    }

    /**
     * 收藏插画
     * @param illustId 插画ID
     * @param restrict
     * @param tags 收藏标签
     * */
    suspend fun postBookmarkAdd(illustId: Int, restrict: Restrict, tags: List<String> = emptyList(), isNovel: Boolean) {
        return httpClient.post {
            url("${BASE_URL}/v1/${if (isNovel) "novel" else "illust"}/bookmark/add")
            parameter("${if (isNovel) "novel" else "illust"}_id", illustId)
            parameter("restrict", restrict.value)
            parameter("tags", tags)
        }.body()
    }

    /**
     * 取消收藏插画
     * @param illustId 插画ID
     * @param isNovel 是否为小说
     * */
    suspend fun postBookmarkDelete(illustId: Int, isNovel: Boolean) {
        return httpClient.post {
            url("${BASE_URL}/v1/${if (isNovel) "novel" else "illust"}/bookmark/delete")
            parameter("${if (isNovel) "novel" else "illust"}_id", illustId)
        }.body()
    }

    /**
     * 关注用户
     * @param userId 用户ID
     * @param restrict
     * */
    suspend fun postFollowAdd(userId: Int, restrict: Restrict) {
        return httpClient.post {
            url("${BASE_URL}/v1/user/follow/add")
            parameter("user_id", userId)
            parameter("restrict", restrict.value)
        }.body()
    }

    /**
     * 取消关注用户
     * @param userId 用户ID
     * */

    suspend fun postFollowDelete(userId: Int) {
        return httpClient.post {
            url("${BASE_URL}/v1/user/follow/delete")
            parameter("user_id", userId)
        }.body()
    }


    /**
     * 添加评论(评论一个插画)
     * @param illustId 插画ID
     * @param comment 评论内容
     * @param stampId 表情包ID
     * @param parentCommentId 父评论ID(用来回复)
     * */
    suspend fun postCommentAdd(
        illustId: Int,
        comment: String = "",
        stampId: Int?,
        parentCommentId: Int? = null
    ): Comment {
        return httpClient.post {
            url("${BASE_URL}/v1/illust/comment/add")
            parameter("illust_id", illustId)
            parameter("comment", comment)
            if (null != stampId) {
                parameter("stamp_Id", parentCommentId)
            }
            if (null != parentCommentId) {
                parameter("parent_comment_id", parentCommentId)
            }
        }.body()
    }

    /**
     * 删除评论(自己的)
     * @param commentId 评论ID
     * */
    suspend fun postCommentDelete(commentId: Int) {
        return httpClient.post {
            url("${BASE_URL}/v1/illust/comment/delete")
            parameter("comment_id", commentId)
        }.body()
    }

    /**
     * 获取所有表情贴图
     * */
    suspend fun getStamps(): StampListResult {
        return httpClient.get {
            url("${BASE_URL}/v1/stamps")
        }.body()
    }
}
