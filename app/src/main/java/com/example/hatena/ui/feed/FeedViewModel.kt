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

    private val _entrySelection = MutableLiveData<HotEntry>()
    val entrySelection: LiveData<HotEntry>
        get() = _entrySelection

    fun fetchHotEntries(kind: ChannelKind) {
        viewModelScope.launch {
            val rss = feedRepository.getHotEntriesBy(kind)
            _entries.value = rss.items
        }
    }

    fun onEntrySelected(entry: HotEntry) {
        _entrySelection.value = entry
    }

    fun doneEntrySelection() {
        _entrySelection.value = null
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