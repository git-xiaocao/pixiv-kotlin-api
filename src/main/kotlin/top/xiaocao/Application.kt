package top.xiaocao


import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import top.xiaocao.api.*
import top.xiaocao.api.model.Search



val api = PixivAPI()

suspend fun testGetUserDetail(){
    //逆流茶会
    val result = api.getUserDetail(50258193)
    val json = Json.encodeToString(result)
    println(json)
}

suspend fun testGetUserBookmarks() {

    val result = api.getUserBookmarks(50258193, true)
    val json = Json.encodeToString(result)
    println(json)
}

suspend fun testGetUserIllusts() {
    val result = api.getUserIllusts(50258193, WorkType.ILLUST)
    val json = Json.encodeToString(result)
    println(json)
}

suspend fun testGetRecommendedIllusts() {
    val result = api.getRecommendedIllusts(WorkType.ILLUST)
    val json = Json.encodeToString(result)
    println(json)
}

suspend fun testGetRecommendedUsers() {
    val result = api.getRecommendedUsers()
    val json = Json.encodeToString(result)
    println(json)
}

suspend fun testGetFollowingUsers() {
    val result = api.getFollowingUsers(50258193)

    val json = Json.encodeToString(result)
    println(json)
}


suspend fun testGetRanking() {

    val result = api.getRanking(RankingMode.DAY)
    val json = Json.encodeToString(result)
    println(json)
}

suspend fun testGetTrendingTags() {
    val result = api.getTrendingTags()
    val json = Json.encodeToString(result)
    println(json)
}

suspend fun testSearch() {

    //花园猫 降序 标签完全匹配

    val result = api.search("abc", SearchSort.DATE_DESC, SearchTarget.EXACT_MATCH_FOR_TAGS)
    result.nextUrl?.let {

        val r: Search = api.next(it)
        val json = Json.encodeToString(r)
        println(json)
    }

    val json = Json.encodeToString(result)
    println(json)
}

suspend fun main() {

    testGetUserDetail()

}