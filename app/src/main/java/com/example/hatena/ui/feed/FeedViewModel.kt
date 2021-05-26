package com.example.hatena.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hatena.api.HatenaApi
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    private val _feedRes = MutableLiveData<String>()
    val feedRes: LiveData<String>
        get() = _feedRes

    init {
        fetchHotEntries()
    }

    private fun fetchHotEntries() {
        val api = HatenaApi.create()
        viewModelScope.launch {
            val rss = api.getHotEntries()
            val resp = "RSS: " + rss.items.joinToString { it.title }
            _feedRes.value = resp
        }
    }
}