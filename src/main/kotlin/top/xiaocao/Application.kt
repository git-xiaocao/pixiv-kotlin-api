package top.xiaocao


import top.xiaocao.api.*
import java.io.File
import java.net.URL


val api = PixivAPI()

//suspend fun testGetUserDetail() {
//    //逆流茶会
//    val result = api.getUserDetail(50258193)
//    val json = Json.encodeToString(result)
//    println(json)
//}
//
//suspend fun testGetUserBookmarks() {
//
//    val result = api.getUserBookmarks(50258193, true)
//    val json = Json.encodeToString(result)
//    println(json)
//}
//
//suspend fun testGetUserIllusts() {
//    val result = api.getUserIllusts(50258193, WorkType.ILLUST)
//    val json = Json.encodeToString(result)
//    println(json)
//}
//
//suspend fun testGetRecommendedIllusts() {
//    val result = api.getRecommendedIllusts(WorkType.ILLUST)
//    val json = Json.encodeToString(result)
//    println(json)
//}
//
//suspend fun testGetRecommendedUsers() {
//    val result = api.getRecommendedUsers()
//    val json = Json.encodeToString(result)
//    println(json)
//}
//
//suspend fun testGetFollowingUsers() {
//    val result = api.getFollowingUsers(50258193)
//
//    val json = Json.encodeToString(result)
//    println(json)
//}
//
//
//suspend fun testGetRanking() {
//
//    val result = api.getRanking(RankingMode.DAY)
//    val json = Json.encodeToString(result)
//    println(json)
//}
//
//suspend fun testGetTrendingTags() {
//    val result = api.getTrendingTags()
//    val json = Json.encodeToString(result)
//    println(json)
//}
//
//suspend fun testSearch() {
//
//    //花园猫 降序 标签完全匹配
//
//    val result = api.search("花园猫", SearchSort.DATE_DESC, SearchTarget.EXACT_MATCH_FOR_TAGS)
//    result.nextUrl?.let {
//
//        val r: Search = api.next(it)
//        val json = Json.encodeToString(r)
//        println(json)
//    }
//
//    val json = Json.encodeToString(result)
//    println(json)
//}


suspend fun main() {

    api.getStamps().let { result ->
        result.stamps.forEach { stamp ->
            stamp.stampUrl
            URL(stamp.stampUrl).openStream().use { inputStream ->

                File("C:\\Users\\Administrator\\Desktop\\stamps\\stamp-${stamp.stampId}.jpg").outputStream()
                    .use { outputStream ->
                        inputStream.copyTo(outputStream)
                        println("下载stamp-${stamp.stampId}.jpg成功")
                    }
            }

        }
    }

}