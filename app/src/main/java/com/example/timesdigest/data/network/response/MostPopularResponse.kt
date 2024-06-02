package com.example.timesdigest.data.network.response

import com.example.timesdigest.data.local.entities.ArticleEntity
import com.example.timesdigest.data.local.entities.ArticleEntityType
import com.example.timesdigest.data.network.util.getImageUrlByResolution
import com.example.timesdigest.model.Article
import com.example.timesdigest.model.ArticleType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MostPopularResponse(
    val status: String,
    val copyright: String,
    @SerialName("num_results")
    val numResults: Long,
    val results: List<MostPopularArticleResponse>,
)

@Serializable
data class MostPopularArticleResponse(
    val uri: String,
    val url: String,
    val id: Long,
    @SerialName("asset_id")
    val assetId: Long,
    val source: String,
    @SerialName("published_date")
    val publishedDate: String,
    val updated: String,
    val section: String,
    val subsection: String,
    val nytdsection: String,
    @SerialName("adx_keywords")
    val adxKeywords: String,
    val byline: String,
    val type: String,
    val title: String,
    val abstract: String,
    @SerialName("des_facet")
    val desFacet: List<String>,
    @SerialName("org_facet")
    val orgFacet: List<String>,
    @SerialName("per_facet")
    val perFacet: List<String>,
    @SerialName("geo_facet")
    val geoFacet: List<String>,
    val media: List<MostPopularMediaResponse>?,
    @SerialName("eta_id")
    val etaId: Long,
)

@Serializable
data class MostPopularMediaResponse(
    val type: String,
    val subtype: String,
    val caption: String,
    val copyright: String,
    @SerialName("approved_for_syndication")
    val approvedForSyndication: Long,
    @SerialName("media-metadata")
    val mediaMetadata: List<MetadataResponse>,
)

@Serializable
data class MetadataResponse(
    val url: String,
    val format: String,
    val height: Long,
    val width: Long,
)

fun MostPopularArticleResponse.asArticle() = Article(
    title = title,
    subtitle = abstract,
    url = url,
    imageUrl = media?.getImageUrlByResolution(),
    type = ArticleType.MOST_POPULAR,
    isSaved = false,
    isFresh = true
)

fun MostPopularArticleResponse.asEntity() = ArticleEntity(
    url = url,
    title = title,
    subtitle = abstract,
    imageUrl = media?.getImageUrlByResolution(),
    type = ArticleEntityType.MOST_POPULAR,
    isSaved = false,
    isFresh = true
)
