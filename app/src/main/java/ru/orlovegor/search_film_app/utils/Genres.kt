package ru.orlovegor.search_film_app.utils

import androidx.annotation.StringRes
import ru.orlovegor.search_film_app.R

enum class Genres(
    val jsonName: String,
    @StringRes
    val  displayName: Int
) {
    EMPTY("",R.string.empty_string),
    COMEDY("комедия", R.string.comedy),
    ACTION("боевик",R.string.action),
    DETECTIVE("детектив",R.string.detective),
    THRILLER("триллер",R.string.thriller),
    HORROR("ужасы",R.string.horror),
    DRAMA("драма",R.string.drama),
    ADVENTURE("приключения",R.string.adventure),
    MILITARY("военный",R.string.military),
    FAMILY("семейный",R.string.family),
    FANTASTIC("фантастика",R.string.fantastic),
    WESTERN("вестерн",R.string.western),
    ANIME("аниме",R.string.anime),
    SPORT("спорт",R.string.sport)
}
