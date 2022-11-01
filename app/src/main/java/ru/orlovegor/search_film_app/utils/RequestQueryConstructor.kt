package ru.orlovegor.search_film_app.utils

import android.util.Log

/**
 *The class creates request maps,
 * since this api requires the same arguments for the type of search (field) and the search query parameter (search).
 * To add a new type of search, you need to add a new type argument to the QueryType class.
 */

data class  RequestQueryConstructor(
    private val query: String,
    private val type: QueryType
) {
    fun makeQuery(): Map<String,String>{
        Log.d("VALUE", " field = ${type.requestValue}, search = query")
        return if (query.isNotBlank()) {
            mapOf(FIELD_NAME to type.requestValue, SEARCH_NAME to query)
        } else {
            emptyMap()
        }

    }

    companion object{
       private const val FIELD_NAME = "field"
      private  const val SEARCH_NAME = "search"
    }

}

