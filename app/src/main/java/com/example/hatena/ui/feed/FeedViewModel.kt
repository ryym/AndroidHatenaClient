package com.example.hatena.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hatena.api.HatenaApi
import com.example.hatena.model.HotEntry
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    private val _entries = MutableLiveData<List<HotEntry>>()
    val entries: LiveData<List<HotEntry>>
        get() = _entries

    private val _navigateToEntry = MutableLiveData<HotEntry>()
    val navigateToEntry: LiveData<HotEntry>
        get() = _navigateToEntry

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

    fun onEntryClick(entry: HotEntry) {
        _navigateToEntry.value = entry
    }

    fun doneEntryNavigation() {
        _navigateToEntry.value = null
    }
}