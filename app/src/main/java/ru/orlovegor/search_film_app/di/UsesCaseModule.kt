package ru.orlovegor.search_film_app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import ru.orlovegor.search_film_app.data.repositories.SearchMovieRepository
import ru.orlovegor.search_film_app.domain.GetMovieByTittleUsesCase

@InstallIn(ViewModelComponent::class)
@Module
object UsesCaseModule {
    @Provides
    fun provideUsesCase(
        @ApplicationContext context: Context,
        searchMovieRepository: SearchMovieRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): GetMovieByTittleUsesCase =
        GetMovieByTittleUsesCase(context, searchMovieRepository, dispatcher)
}