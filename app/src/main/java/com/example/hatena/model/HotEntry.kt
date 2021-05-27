package com.example.hatena.model

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "item")
data class HotEntry(
    @PropertyElement
    val title: String,

    @PropertyElement
    val link: String,

    @PropertyElement
    val description: String,

    @PropertyElement(name = "hatena:bookmarkcount")
    val bookmarkCount: Int,

    @PropertyElement(name = "hatena:imageurl")
    val imageUrl: String?
)