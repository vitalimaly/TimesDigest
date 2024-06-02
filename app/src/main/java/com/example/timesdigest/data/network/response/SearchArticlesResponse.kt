package com.example.timesdigest.data.network.response

import com.example.timesdigest.data.local.entities.ArticleEntity
import com.example.timesdigest.data.local.entities.ArticleEntityType
import com.example.timesdigest.data.network.util.getImageUrlByResolution
import com.example.timesdigest.model.Article
import com.example.timesdigest.model.ArticleType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchArticlesResponse(
    val status: String?,
    val copyright: String?,
    val response: Response,
)

@Serializable
data class Response(
    val docs: List<Doc>,
    val meta: Meta?,
)

@Serializable
data class Doc(
    val abstract: String,
    @SerialName("web_url")
    val webUrl: String,
    val snippet: String?,
    @SerialName("lead_paragraph")
    val leadParagraph: String?,
    @SerialName("print_section")
    val printSection: String?,
    @SerialName("print_page")
    val printPage: String?,
    val source: String?,
    val multimedia: List<SearchArticlesMultimedia>?,
    val headline: Headline,
    val keywords: List<Keyword>?,
    @SerialName("pub_date")
    val pubDate: String?,
    @SerialName("document_type")
    val documentType: String?,
    @SerialName("news_desk")
    val newsDesk: String?,
    @SerialName("section_name")
    val sectionName: String?,
    @SerialName("subsection_name")
    val subsectionName: String?,
    val byline: Byline?,
    @SerialName("type_of_material")
    val typeOfMaterial: String?,
    @SerialName("_id")
    val id: String?,
    @SerialName("word_count")
    val wordCount: Long?,
    val uri: String?,
)

@Serializable
data class SearchArticlesMultimedia(
    val rank: Long?,
    val subtype: String?,
    val caption: String?,
    val credit: String?,
    val type: String,
    val url: String,
    val height: Long,
    val width: Long,
    val subType: String?,
    @SerialName("crop_name")
    val cropName: String?,
)

@Serializable
data class Headline(
    val main: String,
    val kicker: String?,
    @SerialName("content_kicker")
    val contentKicker: String?,
    @SerialName("print_headline")
    val printHeadline: String?,
    val name: String?,
    val seo: String?,
    val sub: String?,
)

@Serializable
data class Keyword(
    val name: String?,
    val value: String?,
    val rank: Long?,
    val major: String?,
)

@Serializable
data class Byline(
    val original: String?,
    val person: List<Person>?,
    val organization: String?,
)

@Serializable
data class Person(
    val firstname: String?,
    val middlename: String?,
    val lastname: String?,
    val qualifier: String?,
    val title: String?,
    val role: String?,
    val organization: String?,
    val rank: Long?,
)

@Serializable
data class Meta(
    val hits: Long?,
    val offset: Long?,
    val time: Long?,
)

fun Doc.asArticle() = Article(
    title = headline.main,
    subtitle = abstract,
    url = webUrl,
    imageUrl = multimedia?.getImageUrlByResolution(600 * 800),
    type = ArticleType.SEARCH,
    isSaved = false,
    isFresh = true
)

fun Doc.asEntity() = ArticleEntity(
    url = webUrl,
    title = headline.main,
    subtitle = abstract,
    imageUrl = multimedia?.getImageUrlByResolution(600 * 800),
    type = ArticleEntityType.SEARCH,
    isSaved = false,
    isFresh = false
)
