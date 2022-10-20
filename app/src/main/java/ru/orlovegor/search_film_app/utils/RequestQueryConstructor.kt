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
        val newMap = linkedMapOf<String, String>()
        if (query.isNotBlank()) {
            newMap[FIELD_NAME] = type.name.lowercase()
            newMap[SEARCH_NAME] = query
        } else {
            newMap[""] = ""
        }
        Log.d("MAKE REQUEST", "Request = $newMap")
        return newMap
    }

    companion object{
       private const val FIELD_NAME = "field"
      private  const val SEARCH_NAME = "search"
    }

}

