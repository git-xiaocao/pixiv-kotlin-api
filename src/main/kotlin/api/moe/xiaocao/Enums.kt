package api.moe.xiaocao

enum class Restrict(val value: String) {
    PUBLIC("public"),
    PRIVATE("private"),
}

enum class RankingMode(val value: String) {
    /** 天 */
    DAY("day"),

    /** 天 R18 */
    DAY_R18("day_r18"),

    /** 天 男性欢迎  */
    DAY_MALE("day_male"),

    /** 天 男性欢迎 R18 */
    DAY_MALE_R18("day_male_r18"),

    /** 天 女性欢迎 */
    DAY_FEMALE("day_female"),

    /** 天 女性欢迎 R18 */
    DAY_FEMALE_R18("day_female_r18"),

    /** 周(原创) */
    WEEK_ORIGINAL("week_original"),

    /** 周(新人) */
    WEEK_ROOKIE("week_rookie"),

    /** 周 */
    WEEK("week"),

    /** 周 R18 */
    WEEK_R18("week_r18"),

    /** 月 */
    MONTH("month"),
}

enum class IllustType(val value: String) {

    /** 插画 */
    ILLUST("illust"),

    /** 漫画  */
    MANGA("manga"),

}

enum class SearchSort(val value: String) {
    /**
     * 时间降序(最新)
     * */
    DATE_DESC("date_desc"),

    /**
     * 时间升序(最旧)
     * */
    DATE_ASC("date_asc"),

    /**
     * 热度降序
     * */
    POPULAR_DESC("popular_desc")
}

enum class SearchIllustTarget(val value: String) {
    /**
     * 标签(部分匹配)
     * */
    PARTIAL_MATCH_FOR_TAGS("partial_match_for_tags"),

    /**
     * 标签(完全匹配)
     * */
    EXACT_MATCH_FOR_TAGS("exact_match_for_tags"),

    /**
     * 标签&简介
     * */
    TITLE_AND_CAPTION("title_and_caption"),
}

enum class SearchNovelTarget(val value: String) {
    /**
     * 标签(部分匹配)
     * */
    PARTIAL_MATCH_FOR_TAGS("partial_match_for_tags"),

    /**
     * 标签(完全匹配)
     * */
    EXACT_MATCH_FOR_TAGS("exact_match_for_tags"),

    /**
     * 文本
     * */
    TEXT("text"),

    /**
     * 关键字
     * */
    KEYWORD("keyword"),
}
