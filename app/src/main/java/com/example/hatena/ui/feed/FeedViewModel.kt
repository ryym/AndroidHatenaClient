package com.example.hatena.ui.feed

import androidx.lifecycle.*
import com.example.hatena.model.ChannelKind
import com.example.hatena.model.HotEntry
import com.example.hatena.repository.HatenaFeedRepository
import kotlinx.coroutines.launch

class FeedViewModel(private val feedRepository: HatenaFeedRepository) : ViewModel() {
    private val _entries = MutableLiveData<List<HotEntry>>()
    val entries: LiveData<List<HotEntry>>
        get() = _entries

    private val _navigateToEntry = MutableLiveData<HotEntry>()
    val navigateToEntry: LiveData<HotEntry>
        get() = _navigateToEntry

    fun fetchHotEntries(kind: ChannelKind) {
        viewModelScope.launch {
            val rss = feedRepository.getHotEntriesBy(kind)
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

class FeedViewModelFactory(private val feedRepository: HatenaFeedRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FeedViewModel(feedRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}