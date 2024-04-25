package com.flybuys.newsapp.model

data class NewsItem(
    val title: String,
    var pubDate: String,
    val link: String,
    val guid: String,
    val thumbnail: String,
    val description: String,
    val content: String,
    val enclosure: Enclosure,
    val categories: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as NewsItem
        if (guid != other.guid) return false
        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + pubDate.hashCode()
        result = 31 * result + link.hashCode()
        result = 31 * result + guid.hashCode()
        result = 31 * result + thumbnail.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + enclosure.hashCode()
        result = 31 * result + categories.contentHashCode()
        return result
    }
}
