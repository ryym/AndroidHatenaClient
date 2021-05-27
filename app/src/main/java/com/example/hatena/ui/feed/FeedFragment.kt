package com.example.hatena.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
                Toast.makeText(context, "Click ${entry.link}!", Toast.LENGTH_LONG).show()
            }
        )
        binding.hotEntryList.adapter = hotEntryListAdapter
        binding.hotEntryList.layoutManager = LinearLayoutManager(activity)

        viewModel.entries.observe(viewLifecycleOwner, Observer {
            it?.let {
                hotEntryListAdapter.submitList(it)
            }
        })

        return binding.root
    }
}