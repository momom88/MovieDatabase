package com.example.martinzamarski.moviedatabasekotlin.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.martinzamarski.moviedatabasekotlin.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun loadAllMovie(): LiveData<List<Movie>>

    @Query("DELETE FROM movie")
    fun deleteMovie()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)
}