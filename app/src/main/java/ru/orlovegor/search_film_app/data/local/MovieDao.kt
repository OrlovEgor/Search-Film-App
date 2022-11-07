package ru.orlovegor.search_film_app.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.orlovegor.search_film_app.data.local.models.MovieLocal

@Dao
interface MovieDao {

    @Query("select * from ${MovieDatabaseContract.TABLE_NAME}")
   fun getMovies(): Flow<List<MovieLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend  fun insertMovie(movie: MovieLocal)

    @Delete
   suspend fun deleteMovie(movie: MovieLocal)

   @Query("select ${MovieDatabaseContract.Columns.ID}  from ${MovieDatabaseContract.TABLE_NAME}")
   fun getMoviesId(): Flow<List<Long>>

}