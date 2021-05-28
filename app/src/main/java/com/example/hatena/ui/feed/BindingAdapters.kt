package com.example.hatena.ui.feed

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


@BindingAdapter("bookmarkCount")
fun TextView.bookmarkCount(count: Int?) {
    count?.let {
        text = "$count users"
    }
}

@BindingAdapter("srcUrl")
fun ImageView.srcUrl(url: String?) {
    url?.let {
        Glide
            .with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}