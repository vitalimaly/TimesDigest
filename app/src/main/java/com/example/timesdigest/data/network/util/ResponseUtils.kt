package com.example.timesdigest.data.network.util

import com.example.timesdigest.data.network.response.MediaResponseFormat.IMAGE
import com.example.timesdigest.data.network.response.MostPopularMediaResponse
import com.example.timesdigest.data.network.response.SearchArticlesMultimedia
import com.example.timesdigest.data.network.response.TopStoryMultimedia
import kotlin.math.abs

@JvmName("getImageUrlByResolutionMostPopularMediaResponse")
fun List<MostPopularMediaResponse>.getImageUrlByResolution(desiredArea: Int = Int.MAX_VALUE) =
    this.map {
        it.type to it.mediaMetadata
    }.flatMap { (type, media) ->
        media.map {
            MultimediaToIterate(
                height = it.height,
                width = it.width,
                type = type,
                url = it.url
            )
        }
    }.getImageUrlByResolution(desiredArea)

@JvmName("getImageUrlByResolutionTopStoryMultimedia")
fun List<TopStoryMultimedia>.getImageUrlByResolution(desiredArea: Int = Int.MAX_VALUE) =
    this.map {
        MultimediaToIterate(
            height = it.height,
            width = it.width,
            type = it.type,
            url = it.url
        )
    }.getImageUrlByResolution(desiredArea)

@JvmName("getImageUrlByResolutionSearchArticlesMultimedia")
fun List<SearchArticlesMultimedia>.getImageUrlByResolution(desiredArea: Int = Int.MAX_VALUE) =
    this.map {
        MultimediaToIterate(
            height = it.height,
            width = it.width,
            type = it.type,
            url = "https://static01.nyt.com/${it.url}"
        )
    }.getImageUrlByResolution(desiredArea)

@JvmName("getImageUrlByResolutionMultimediaToIterate")
fun List<MultimediaToIterate>.getImageUrlByResolution(desiredArea: Int = Int.MAX_VALUE) =
    this.filter {
        it.type.equals(IMAGE.format, ignoreCase = true)
    }.minByOrNull {
        abs(desiredArea - it.height * it.width)
    }?.url

class MultimediaToIterate(
    val height: Long,
    val width: Long,
    val type: String,
    val url: String
)