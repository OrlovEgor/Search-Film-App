package ru.orlovegor.search_film_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.orlovegor.search_film_app.data.local.MovieDatabase.Companion.DB_VERSION
import ru.orlovegor.search_film_app.data.local.models.MovieLocal

@Database(
    entities = [
        MovieLocal::class
    ],
    version = DB_VERSION
)


abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

    companion object {
        const val DB_VERSION = 1
    }
}