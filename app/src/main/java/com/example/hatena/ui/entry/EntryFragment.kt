package com.example.hatena.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hatena.databinding.FragmentEntryBinding

class EntryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = EntryFragmentArgs.fromBundle(arguments)

        val binding = FragmentEntryBinding.inflate(inflater)
        binding.tmpLinkText.text = args.url
        return binding.root
    }
}