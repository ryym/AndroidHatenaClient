package com.example.hatena.api

import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.TypeConverter
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.http.GET

val tikXml: TikXml = TikXml.Builder()
    .addTypeConverter(String::class.java, HtmlEscapeStringConverter())
    .exceptionOnUnreadXml(false)
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://b.hatena.ne.jp")
    .addConverterFactory(TikXmlConverterFactory.create(tikXml))
    .build()

interface HatenaApi {
    companion object {
        fun create(): HatenaApi {
            return retrofit.create(HatenaApi::class.java)
        }
    }

    @GET("hotentry.rss")
    suspend fun getHotEntries(): Rss
}


@Xml(name = "rdf:RDF")
data class Rss(
    @Element(name = "item")
    val items: List<HotEntry>
)

@Xml(name = "item")
data class HotEntry(
    @PropertyElement
    val title: String,

    @PropertyElement
    val link: String,

    @PropertyElement
    val description: String,

    @PropertyElement(name = "hatena:bookmarkCount")
    val bookmarkCount: Int
)

private class HtmlEscapeStringConverter : TypeConverter<String> {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun read(value: String?): String {
        return Html.fromHtml(value ?: "", Html.FROM_HTML_MODE_COMPACT).toString()
    }

    override fun write(value: String?): String {
        throw Error("not supported")
    }
}