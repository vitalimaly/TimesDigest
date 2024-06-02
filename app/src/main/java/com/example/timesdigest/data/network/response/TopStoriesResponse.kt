package com.example.timesdigest.data.network.response

import com.example.timesdigest.data.local.entities.ArticleEntity
import com.example.timesdigest.data.local.entities.ArticleEntityType
import com.example.timesdigest.data.network.util.getImageUrlByResolution
import com.example.timesdigest.model.Article
import com.example.timesdigest.model.ArticleType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopStoriesResponse(
    val status: String,
    val copyright: String,
    val section: String,
    @SerialName("last_updated")
    val lastUpdated: String,
    @SerialName("num_results")
    val numResults: Long,
    val results: List<TopStoryResponse>,
)

@Serializable
data class TopStoryResponse(
    val section: String,
    val subsection: String,
    val title: String,
    val abstract: String,
    val url: String,
    val uri: String,
    val byline: String,
    @SerialName("item_type")
    val itemType: String,
    @SerialName("updated_date")
    val updatedDate: String,
    @SerialName("created_date")
    val createdDate: String,
    @SerialName("published_date")
    val publishedDate: String,
    @SerialName("material_type_facet")
    val materialTypeFacet: String,
    val kicker: String,
    @SerialName("des_facet")
    val desFacet: List<String>,
    @SerialName("org_facet")
    val orgFacet: List<String>,
    @SerialName("per_facet")
    val perFacet: List<String>,
    @SerialName("geo_facet")
    val geoFacet: List<String>,
    val multimedia: List<TopStoryMultimedia>?,
    @SerialName("short_url")
    val shortUrl: String,
)

@Serializable
data class TopStoryMultimedia(
    val url: String,
    val format: String,
    val height: Long,
    val width: Long,
    val type: String,
    val subtype: String,
    val caption: String,
    val copyright: String,
)

fun TopStoryResponse.asArticle() = Article(
    title = title,
    subtitle = abstract,
    url = url,
    imageUrl = multimedia?.getImageUrlByResolution(),
    type = ArticleType.TOP_STORY,
    isSaved = false,
    isFresh = true
)

fun TopStoryResponse.asEntity() = ArticleEntity(
    url = url,
    title = title,
    subtitle = abstract,
    imageUrl = multimedia?.getImageUrlByResolution(),
    type = ArticleEntityType.TOP_STORY,
    isSaved = false,
    isFresh = true
)
