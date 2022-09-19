package ru.orlovegor.search_film_app.data.models.remote_models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class MovieParseAdapter {

    @JsonClass(generateAdapter = true)
    data class Search(
        @Json(name = "docs")
        val listRemoteMovie: List<RemoteMovie>
    )
}