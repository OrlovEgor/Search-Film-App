package ru.orlovegor.search_film_app.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectionInterceptor (@ApplicationContext private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        return if (!isConnected()) {
            Log.d("app", "throw exception")
            throw IOException(" Check internet connection")
        } else {
            chain.proceed(originalRequest)
        }
    }
    @Suppress("DEPRECATION")
    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connectionInfo = connectivityManager.activeNetworkInfo
        return connectionInfo != null && connectionInfo.isConnected
    }


}