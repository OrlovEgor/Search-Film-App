package ru.orlovegor.search_film_app.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.orlovegor.search_film_app.data.local.MovieDatabaseContract

@Entity(
    tableName = MovieDatabaseContract.TABLE_NAME
)
data class MovieLocal(
    @PrimaryKey
    @ColumnInfo(name = MovieDatabaseContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = MovieDatabaseContract.Columns.TITTLE)
    val title: String,
    @ColumnInfo(name = MovieDatabaseContract.Columns.POSTER)
    val posterUrl: String,
    @ColumnInfo(name = MovieDatabaseContract.Columns.RELEASE_DATE)
    val releaseDate: String,
    @ColumnInfo(name = MovieDatabaseContract.Columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = MovieDatabaseContract.Columns.SHORT_DESCRIPTION)
    val shortDescription: String,
    @ColumnInfo(name = MovieDatabaseContract.Columns.RATING)
    val rating: Double,
    @ColumnInfo(name = MovieDatabaseContract.Columns.AGE_RESTRICTION)
    val ageRestriction: String,
)

