import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import api.moe.xiaocao.*
import org.junit.Before
import org.junit.Test
import java.io.File
import java.net.URL


class SampleTest {
    private val json = Json {
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
    private val jsonFile = File("./userAccount.json")

    private lateinit var api: PixivApi

    //Neps
    private val testUserId = 50258193

    @Before
    fun before() {
        api = PixivApi(json.decodeFromString(jsonFile.readText())) {
            jsonFile.writeText(json.encodeToString(it))
        }
    }


    //获取用户详细信息
    @Test
    fun testGetUserDetail() {
        runBlocking {
            //Neps
            api.getUserDetail(testUserId)
        }.let { result ->
            println(result)
        }
    }

    //获取用户收藏
    @Test
    fun testGetUserBookmarks() {
        runBlocking {
            api.getUserIllustBookmarkPage(testUserId, Restrict.PUBLIC)
        }.let { result ->
            println(result)
        }
    }

    //获取用户插画
    @Test
    fun testGetUserIllusts() {
        runBlocking {
            api.getUserIllustPage(50258193, IllustType.ILLUST)
        }.let { result ->
            println(result)
        }
    }

    //获取推荐插画
    @Test
    fun testGetRecommendedIllustPage() {
        runBlocking {
            api.getRecommendedIllustPage(IllustType.ILLUST)
        }.let { result ->
            result.illusts.forEach {  }
            println(result)
        }
    }

    //获取推荐用户
    @Test
    fun testGetRecommendedUserPage() {
        runBlocking {
            api.getRecommendedUserPage()
        }.let { result ->
            println(result)
        }
    }

    //获取关注的用户
    @Test
    fun testGetFollowingUsers() {
        runBlocking {
            api.getFollowingUserPage(testUserId, Restrict.PUBLIC)
        }.let { result ->
            println(result)
        }
    }

    //获取排行榜
    @Test
    fun testGetRanking() {
        runBlocking {
            api.getRankingPage(RankingMode.DAY)
        }.let { result ->
            println(result)
        }
    }

    //获取搜索推荐标签

    @Test
    fun testGetTrendingTags() {
        runBlocking {
            api.getTrendingTagList()
        }.let { result ->
            println(result)
        }
    }

    @Test
    //测试搜索
    fun testSearch() {
        runBlocking {
            //花园猫 降序 标签完全匹配
            api.getSearchIllustPage("花园猫", SearchSort.DATE_DESC, SearchIllustTarget.EXACT_MATCH_FOR_TAGS)
        }.let { result ->
            println(result)
        }
    }

    //下载表情贴图
    @Test
    fun testDownloadStamps() {
        runBlocking {
            api.getStamps().let { result ->

                val saveDir = File("C:\\Users\\XiaoCao\\Desktop\\stamps")
                if (!saveDir.exists() && !saveDir.isDirectory) {
                    saveDir.mkdir()
                }

                result.stamps.forEach { stamp ->
                    stamp.stampUrl
                    URL(stamp.stampUrl).openStream().use { inputStream ->

                        File(saveDir, "stamp-${stamp.stampId}.jpg").outputStream()
                            .use { outputStream ->
                                inputStream.copyTo(outputStream)
                                println("下载stamp-${stamp.stampId}.jpg成功")
                            }
                    }

                }
            }
            println("结束")
        }

    }

}
