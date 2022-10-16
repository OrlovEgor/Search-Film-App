package ru.orlovegor.search_film_app.data.local

object MovieDatabaseContract {

    const val TABLE_NAME = "movie"

    object Columns {
        const val ID = "id"
        const val TITTLE = "tittle"
        const val POSTER = "poster_url"
        const val RELEASE_DATE = "release_date"
        const val DESCRIPTION = "description"
        const val SHORT_DESCRIPTION = "short_description"
        const val RATING = "rating"
        const val AGE_RESTRICTION = "age_restriction"
        const val IS_FAVORITE = "is_favorite"

    }


}