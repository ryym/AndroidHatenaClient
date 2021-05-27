package com.example.hatena.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hatena.api.HatenaApi
import com.example.hatena.api.HotEntry
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    private val _entries = MutableLiveData<List<HotEntry>>()
    val entries: LiveData<List<HotEntry>>
        get() = _entries

    init {
        fetchHotEntries()
    }

    private fun fetchHotEntries() {
        val api = HatenaApi.create()
        viewModelScope.launch {
            val rss = api.getHotEntries()
            _entries.value = rss.items
        }
    }
}