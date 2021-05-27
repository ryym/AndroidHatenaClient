package com.example.hatena.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hatena.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {
    private val viewModel: FeedViewModel by lazy {
        ViewModelProvider(this).get(FeedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val hotEntryListAdapter = HotEntryListAdapter(
            onEntryClick = HotEntryClickListener { entry ->
                viewModel.onEntryClick(entry)
            }
        )
        binding.hotEntryList.adapter = hotEntryListAdapter
        binding.hotEntryList.layoutManager = LinearLayoutManager(activity)

        viewModel.entries.observe(viewLifecycleOwner, {
            it?.let {
                hotEntryListAdapter.submitList(it)
            }
        })

        viewModel.navigateToEntry.observe(viewLifecycleOwner, { entry ->
            entry?.let {
                val action = FeedFragmentDirections.actionFeedToEntry(entry.link)
                findNavController().navigate(action)
                viewModel.doneEntryNavigation()
            }
        })

        return binding.root
    }
}