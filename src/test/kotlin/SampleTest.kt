import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.xiaocao.*
import org.junit.Before
import org.junit.Test
import java.io.File
import java.net.URL


class SampleTest {

    private val jsonFile = File("./userAccount.json")

    private lateinit var apiClient: ApiClient

    //Neps
    private val testUserId = 50258193

    @Before
    fun before() {
        apiClient = ApiClient(Json.decodeFromString(jsonFile.readText())) {
            jsonFile.writeText(Json.encodeToString(it))
        }
    }
    

    //获取用户详细信息
    @Test
    fun testGetUserDetail() {
        runBlocking {
            //Neps
            apiClient.getUserDetail(testUserId)
        }.let { result ->
            println(result)
        }
    }

    //获取用户收藏
    @Test
    fun testGetUserBookmarks() {
        runBlocking {
            apiClient.getUserIllustBookmarks(testUserId, Restrict.Public)
        }.let { result ->
            println(result)
        }
    }

    //获取用户插画
    @Test
    fun testGetUserIllusts() {
        runBlocking {
            apiClient.getUserIllusts(50258193, IllustType.ILLUST)
        }.let { result ->
            println(result)
        }
    }

    //获取推荐插画
    @Test
    fun testGetRecommendedIllusts() {
        runBlocking {
            apiClient.getRecommendedIllusts(IllustType.ILLUST)
        }.let { result ->
            println(result)
        }
    }

    //获取推荐用户
    @Test
    fun testGetRecommendedUsers() {
        runBlocking {
            apiClient.getRecommendedUsers()
        }.let { result ->
            println(result)
        }
    }

    //获取关注的用户
    @Test
    fun testGetFollowingUsers() {
        runBlocking {
            apiClient.getFollowingUsers(testUserId,Restrict.Public)
        }.let { result ->
            println(result)
        }
    }

    //获取排行榜
    @Test
    fun testGetRanking() {
        runBlocking {
            apiClient.getRanking(RankingMode.DAY)
        }.let { result ->
            println(result)
        }
    }

    //获取搜索推荐标签

    @Test
    fun testGetTrendingTags() {
        runBlocking {
            apiClient.getTrendingTags()
        }.let { result ->
            println(result)
        }
    }

    @Test
    //测试搜索
    fun testSearch() {
        runBlocking {
            //花园猫 降序 标签完全匹配
            apiClient.searchIllust("花园猫", SearchSort.DATE_DESC, SearchTarget.EXACT_MATCH_FOR_TAGS)
        }.let { result ->
            println(result)
        }
    }

    //下载表情贴图
    @Test
    fun testDownloadStamps() {
        runBlocking {
            apiClient.getStamps().let { result ->

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