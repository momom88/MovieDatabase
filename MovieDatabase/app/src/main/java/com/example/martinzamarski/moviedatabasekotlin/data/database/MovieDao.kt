package com.example.martinzamarski.moviedatabasekotlin.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun loadAllMovie(): Flowable<List<Movie>>

    @Query("DELETE FROM movie")
    fun deleteMovie()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)
}