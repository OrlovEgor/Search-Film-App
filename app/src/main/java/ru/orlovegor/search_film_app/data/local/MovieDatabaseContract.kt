package ru.orlovegor.search_film_app.data.local

object MovieDatabaseContract {

    const val TABLE_NAME = "movie"

    object Columns {
        const val ID = "id"
        const val TITTLE = "tittle"
        const val POSTER = "poster_url"
        const val RELEASE_DATE = "releaseDate"
        const val DESCRIPTION = "description"
        const val SHORT_DESCRIPTION = "shortDescription"
        const val RATING = "rating"
        const val AGE_RESTRICTION = "ageRestriction"
        const val SIMILAR_MOVIES = "similarMovie"

    }


}