package ru.orlovegor.search_film_app.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.orlovegor.search_film_app.data.local.MovieDao
import ru.orlovegor.search_film_app.data.local.MovieDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext conext: Context): MovieDatabase =
        Room.databaseBuilder(
            conext.applicationContext,
            MovieDatabase::class.java,
            DB_NAME
        ).build()

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao = movieDatabase.getMovieDao()
    private const val DB_NAME = "movie_database"
}