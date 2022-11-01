package ru.orlovegor.search_film_app.utils

enum class QueryType(val requestValue: String){
    NAME("name"),
    YEAR("year"),
    Genre("genres.name"),
    Rating("rating.kp")
}