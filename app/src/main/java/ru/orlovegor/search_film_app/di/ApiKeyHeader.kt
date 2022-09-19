package ru.orlovegor.search_film_app.di

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyHeader(
    private val queryName: String,
    private val queryHeader: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request() //получаем оригинальный запрос

        val originalUrl = originalRequest.url //получаем его url
        val url = originalUrl.newBuilder().addQueryParameter(
            queryName,
            queryHeader
        ) // добавляем в оригинальный url query параеметры
            .build()

        val modifiedRequest =
            originalRequest.newBuilder() // создаем подифицированный рапрос с новым урлом
                .url(url)
                .build()

        return chain.proceed(modifiedRequest) // отправляем его в выполнение
    }
}