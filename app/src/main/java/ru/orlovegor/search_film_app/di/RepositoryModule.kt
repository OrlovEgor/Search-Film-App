package ru.orlovegor.search_film_app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.orlovegor.search_film_app.data.repositories.*

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideSearchRepository(impl: SearchMovieRepositoryImpl): SearchMovieRepository

    @Binds
    abstract fun provideFullDescriptionRepository(impl: FullDescriptionRepositoryImpl): FullDescriptionRepository

    @Binds
    abstract fun provideFavoriteRepository(impl: FavoriteMovieRepositoryImpl): FavoriteMovieRepository
}