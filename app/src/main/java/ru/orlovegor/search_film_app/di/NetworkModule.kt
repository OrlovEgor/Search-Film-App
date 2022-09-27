package ru.orlovegor.search_film_app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.orlovegor.search_film_app.data.models.MovieApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val queryName = "token"
    private const val API_KEY = "N45CTXD-M7A4S5S-NZYANQ2-5JDQDRF"
    private const val Base_URL = "https://api.kinopoisk.dev"


    @Provides
    @Singleton
    fun provideOkHttp() =
        OkHttpClient.Builder()
            .addNetworkInterceptor(ApiKeyHeader(queryName, API_KEY))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okhttp: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Base_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okhttp)
        .build()

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

}