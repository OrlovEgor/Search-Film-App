package ru.orlovegor.search_film_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.orlovegor.search_film_app.data.local.models.MovieLocal

@Dao
interface MovieDao {

    @Query("select * from ${MovieDatabaseContract.TABLE_NAME}")
    fun getMovies(): Flow<List<MovieLocal>>

    @Query("select * from ${MovieDatabaseContract.TABLE_NAME} where ${MovieDatabaseContract.Columns.ID} ")
    fun getMoviesId(): Flow<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieLocal)

    @Query("delete from ${MovieDatabaseContract.TABLE_NAME} where ${MovieDatabaseContract.Columns.ID} = :movieId ")
    fun deleteMovie(movieId: Long)
}