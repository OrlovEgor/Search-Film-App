package ru.orlovegor.search_film_app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.orlovegor.search_film_app.data.remote.ApiKeyHeader
import ru.orlovegor.search_film_app.data.remote.ConnectionInterceptor
import ru.orlovegor.search_film_app.data.remote.MovieApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val API_KEY = "N45CTXD-M7A4S5S-NZYANQ2-5JDQDRF"
    private const val Base_URL = "https://api.kinopoisk.dev"

    @Provides
    @Singleton
    fun provideOkHttp(
        @LoggingInterceptorOkHttpClient loggingInterceptor: Interceptor,
        @ConnectionInterceptorOkHttpClient connectionInterceptor: Interceptor,
        @ApiKeyInterceptorOkHttpClient apiKeyInterceptor: Interceptor
    ) =
        OkHttpClient.Builder()
            .addInterceptor(connectionInterceptor)
            .addNetworkInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
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

    @ApiKeyInterceptorOkHttpClient
    @Provides
    @Singleton
    fun provideApiKeyInterceptorOkHttpClient(): Interceptor {
        return ApiKeyHeader(API_KEY)
    }

    @ConnectionInterceptorOkHttpClient
    @Provides
    @Singleton
    fun provideConnectionInterceptorOkHttpClient(@ApplicationContext context: Context): Interceptor {
        return ConnectionInterceptor(context)
    }

    @LoggingInterceptorOkHttpClient
    @Provides
    @Singleton
    fun provideLoggingInterceptorOkHttpClient(
    ): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}