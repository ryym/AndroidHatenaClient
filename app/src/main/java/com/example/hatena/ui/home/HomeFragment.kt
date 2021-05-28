package com.example.hatena.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hatena.R
import com.example.hatena.databinding.FragmentHomeBinding
import com.example.hatena.model.ChannelKind
import com.example.hatena.model.HotEntry
import com.example.hatena.ui.feed.FeedFragment
import com.google.android.material.tabs.TabLayoutMediator

val tabs = listOf(
    ChannelKind.ALL,
    ChannelKind.SOCIAL,
    ChannelKind.ECONOMICS,
    ChannelKind.LIFE,
)

class HomeFragment : Fragment(), FeedFragment.OnEntrySelectedListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        val pagerAdapter = FeedFragmentStateAdapter(this, this)
        binding.feedViewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.feedTabs, binding.feedViewPager) { tab, position ->
            val titleId = tabTitleId(tabs[position])
            tab.text = resources.getString(titleId)
        }.attach()

        return binding.root
    }

    private fun tabTitleId(kind: ChannelKind): Int {
        return when (kind) {
            ChannelKind.ALL -> R.string.channel_kind_all
            ChannelKind.SOCIAL -> R.string.channel_kind_social
            ChannelKind.ECONOMICS -> R.string.channel_kind_economics
            ChannelKind.LIFE -> R.string.channel_kind_life
        }
    }

    override fun onEntrySelected(entry: HotEntry) {
        val action = HomeFragmentDirections.actionOpenEntry(entry.title, entry.link)
        findNavController().navigate(action)
    }
}

class FeedFragmentStateAdapter(
    fragment: Fragment,
    private val onEntrySelected: FeedFragment.OnEntrySelectedListener
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        val kind = tabs[position]
        return FeedFragment.create(kind, onEntrySelected)
    }
}