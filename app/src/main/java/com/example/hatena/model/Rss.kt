package com.example.hatena.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rdf:RDF")
data class Rss(
    @Element(name = "item")
    val items: List<HotEntry>
)