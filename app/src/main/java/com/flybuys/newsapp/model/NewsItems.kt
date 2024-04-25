package com.flybuys.newsapp.model

data class NewsItems(val status: String, val feed: Feed, val items: Array<NewsItems>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewsItems

        if (status != other.status) return false
        if (feed != other.feed) return false
        if (!items.contentEquals(other.items)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + feed.hashCode()
        result = 31 * result + items.contentHashCode()
        return result
    }
}
