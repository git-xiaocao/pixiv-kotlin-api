import kotlinx.coroutines.*
import org.junit.Test
import top.xiaocao.api.*
import java.io.File
import java.net.URL

@DelicateCoroutinesApi
class SampleTest {

    private val api = PixivAPI("你的token")

    //Neps
    private val testUserId = 50258193

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
            api.getUserBookmarks(testUserId, true)
        }.let { result ->
            println(result)
        }
    }

    //获取用户插画
    @Test
    fun testGetUserIllusts() {
        runBlocking {
            api.getUserIllusts(50258193, WorkType.ILLUST)
        }.let { result ->
            println(result)
        }
    }

    //获取推荐插画
    @Test
    fun testGetRecommendedIllusts() {
        runBlocking {
            api.getRecommendedIllusts(WorkType.ILLUST)
        }.let { result ->
            println(result)
        }
    }

    //获取推荐用户
    @Test
    fun testGetRecommendedUsers() {
        runBlocking {
            api.getRecommendedUsers()
        }.let { result ->
            println(result)
        }
    }

    //获取关注的用户
    @Test
    fun testGetFollowingUsers() {
        runBlocking {
            api.getFollowingUsers(testUserId)
        }.let { result ->
            println(result)
        }
    }

    //获取排行榜
    @Test
    fun testGetRanking() {
        runBlocking {
            api.getRanking(RankingMode.DAY)
        }.let { result ->
            println(result)
        }
    }

    //获取搜索推荐标签

    @Test
    fun testGetTrendingTags() {
        runBlocking {
            api.getTrendingTags()
        }.let { result ->
            println(result)
        }
    }

    @Test
    //测试搜索
    fun testSearch() {
        runBlocking {
            //花园猫 降序 标签完全匹配
            api.search("花园猫", SearchSort.DATE_DESC, SearchTarget.EXACT_MATCH_FOR_TAGS)
        }.let { result ->
            println(result)
        }
    }

    //下载表情贴图
    @Test
    fun testDownloadStamps() {
        runBlocking {
            api.getStamps().let { result ->

                val saveDir = File("C:\\Users\\Administrator\\Desktop\\stamps")
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