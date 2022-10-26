package ru.orlovegor.search_film_app.utils

import androidx.annotation.StringRes
import ru.orlovegor.search_film_app.R

enum class Genres(
    val jsonName: String,
    @StringRes
    val  displayName: Int
) {
    EMPTY("",R.string.empty_string),
    COMEDY("komediya", R.string.comedy),
    ACTION("boevik",R.string.action),
    DETECTIVE("detektiv",R.string.detective),
    THRILLER("triller",R.string.thriller),
    HORROR("uzhasy",R.string.horror),
    DRAMA("drama",R.string.drama),
    ADVENTURE("priklyucheniya",R.string.adventure),
    MILITARY("voennyj",R.string.military),
    FAMILY("semejnyj",R.string.family),
    FANTASTIC("fantastika",R.string.fantastic),
    WESTERN("vestern",R.string.western),
    ANIME("anime",R.string.anime),
    SPORT("sport",R.string.sport)
}