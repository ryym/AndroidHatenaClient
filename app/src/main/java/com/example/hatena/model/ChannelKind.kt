package com.example.hatena.model

enum class ChannelKind(val id: String) {
    ALL("all"),
    SOCIAL("social"),
    ECONOMICS("economics"),
    LIFE("life");

    companion object {
        fun fromId(id: String?): ChannelKind? = when (id) {
            "all" -> ALL
            "social" -> SOCIAL
            "economics" -> ECONOMICS
            "life" -> LIFE
            else -> null
        }
    }
}