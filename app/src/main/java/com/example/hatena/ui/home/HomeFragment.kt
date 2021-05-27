package com.example.hatena.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hatena.databinding.FragmentHomeBinding
import com.example.hatena.ui.feed.FeedFragment
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        val pagerAdapter = FeedFragmentStateAdapter(this)
        binding.feedViewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.feedTabs, binding.feedViewPager) { tab, position ->
            tab.text = "Tab: $position"
        }.attach()

        return binding.root
    }
}

class FeedFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FeedFragment()
        fragment.arguments = Bundle().also {
            it.putString("test", "tab: $position")
        }
        return fragment
    }
}