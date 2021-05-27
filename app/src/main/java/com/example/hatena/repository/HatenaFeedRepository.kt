package com.example.hatena.repository

import com.example.hatena.api.HatenaApi
import com.example.hatena.model.ChannelKind
import com.example.hatena.model.Rss

class HatenaFeedRepository(private val api: HatenaApi) {
    companion object {
        fun create(): HatenaFeedRepository {
            return HatenaFeedRepository(HatenaApi.create())
        }
    }

    suspend fun getHotEntriesBy(kind: ChannelKind): Rss {
        return when (kind) {
            ChannelKind.ALL -> api.getHotEntries()
            ChannelKind.SOCIAL -> api.getSocialHotEntries()
            ChannelKind.ECONOMICS -> api.getEconomicsHotEntries()
            ChannelKind.LIFE -> api.getLifeHotEntries()
        }
    }
}