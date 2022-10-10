package ru.orlovegor.search_film_app.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val title: String,
    @Json(name = "year")
    val releaseDate: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "shortDescription")
    val shortDescription: String?,
    @Json(name = "rating")
    val rating: RatingMovie,
    @Json(name = "poster")
    val posterUrl: PosterMovie?,
    @Json(name = "ratingMpaa")
    val ageRestriction: AgeRestriction?,
    @Json(name = "similarMovies")
    val similarMovieDto: List<SimilarMovieDto?>?
)

@JsonClass(generateAdapter = true)
data class PosterMovie(
    @Json(name = "url")
    val imgUrl: String = "",
    @Json(name = "previewUrl")
    val poster: String = ""
)

@JsonClass(generateAdapter = true)
data class RatingMovie(
    @Json(name = "kp")
    val kp: Double = 0.0,
    @Json(name = "imdb")
    val imdb: Double = 0.0
)

@JsonClass(generateAdapter = true)
data class SimilarMovieDto(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "name")
    val tittle: String?,
    @Json(name = "poster")
    val imageUrl: PosterMovie?

)

enum class AgeRestriction(val age: String) {
    @Json(name = "g")
    GENERAL("0+"),

    @Json(name = "pg")
    PG("6+"),

    @Json(name = "pg13")
    PG_13("13+"),

    @Json(name = "r")
    R("16+"),

    @Json(name = "nc17")
    NC_17("18+")
}




