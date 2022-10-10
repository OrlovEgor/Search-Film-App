package ru.orlovegor.search_film_app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.orlovegor.search_film_app.data.repositories.FullDescriptionRepository
import ru.orlovegor.search_film_app.data.repositories.SearchMovieRepository
import ru.orlovegor.search_film_app.data.repositories.SearchMovieRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideSearchRepository(impl: SearchMovieRepositoryImpl): SearchMovieRepository

    @Binds
    abstract fun provideFullDescriptionRepository(impl: SearchMovieRepositoryImpl): FullDescriptionRepository
}