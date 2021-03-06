package com.example.hatena.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hatena.databinding.FragmentEntryBinding

class EntryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args = EntryFragmentArgs.fromBundle(arguments)

        val activity = requireActivity() as? AppCompatActivity
        activity?.supportActionBar?.also {
            it.title = args.title
        }

        val binding = FragmentEntryBinding.inflate(inflater)
        binding.entryWebview.settings.javaScriptEnabled = true
        binding.entryWebview.loadUrl(args.url)
        return binding.root
    }
}