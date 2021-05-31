package com.example.hatena.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hatena.R
import com.example.hatena.databinding.FragmentFeedBinding
import com.example.hatena.model.ChannelKind
import com.example.hatena.model.HotEntry
import com.example.hatena.repository.HatenaFeedRepository

private const val ARG_CHANNEL_KIND = "channel_kind"

class FeedFragment private constructor() : Fragment() {
    companion object {
        fun create(kind: ChannelKind, onEntrySelected: OnEntrySelectedListener): FeedFragment {
            val fragment = FeedFragment()
            fragment.arguments = Bundle().also {
                it.putString(ARG_CHANNEL_KIND, kind.id)
            }
            fragment.setOnEntrySelectedListener(onEntrySelected)
            return fragment
        }
    }

    private lateinit var viewModel: FeedViewModel
    private var onEntrySelected: OnEntrySelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = FeedViewModelFactory(HatenaFeedRepository.create())
        viewModel = ViewModelProvider(this, viewModelFactory).get(FeedViewModel::class.java)

        val binding = FragmentFeedBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val args = Args.fromArguments(requireArguments())

        val hotEntryListAdapter = HotEntryListAdapter(
            onEntryClick = HotEntryClickListener { entry ->
                viewModel.onEntrySelected(entry)
            }
        )

        viewModel.fetchHotEntries(args.channelKind)

        viewModel.entries.observe(viewLifecycleOwner, {
            it?.let {
                hotEntryListAdapter.submitList(it)
                binding.hotEntryListSwipeContainer.isRefreshing = false
            }
        })

        binding.hotEntryList.also {
            it.adapter = hotEntryListAdapter
            val layoutManager = LinearLayoutManager(activity)
            it.layoutManager = layoutManager
            it.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
        }

        binding.hotEntryListSwipeContainer.also {
            val primaryColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
            it.setColorSchemeColors(primaryColor)
            it.setOnRefreshListener {
                viewModel.fetchHotEntries(args.channelKind)
            }
        }

        viewModel.entrySelection.observe(viewLifecycleOwner, { entry ->
            entry?.let {
                onEntrySelected?.onEntrySelected(entry)
                viewModel.doneEntrySelection()
            }
        })

        return binding.root
    }

    fun setOnEntrySelectedListener(callback: OnEntrySelectedListener) {
        this.onEntrySelected = callback
    }

    interface OnEntrySelectedListener {
        fun onEntrySelected(entry: HotEntry)
    }

    private data class Args(val channelKind: ChannelKind) {
        companion object {
            fun fromArguments(arguments: Bundle): Args {
                val channelKind = ChannelKind.fromId(arguments.getString(ARG_CHANNEL_KIND))
                return Args(requireNotNull(channelKind))
            }
        }
    }
}