package com.example.martinzamarski.moviedatabasekotlin.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.martinzamarski.moviedatabasekotlin.data.database.AppDatabase
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test the implementation of [UserDao]
 */
@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        )
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getMoviesWhenNoMoviesInserted() {
        database.movieDao().loadAllMovie()
            .test()
            .assertValue{it.isEmpty()}
    }

    @Test
    fun insertAndGetMovies() {

        database.movieDao().insertMovie(MOVIE)

        database.movieDao().loadAllMovie()
            .test()
            .assertValue { it[0].id == MOVIE.id }
    }

    @Test
    fun deleteAllMovie() {
        database.movieDao().insertMovie(MOVIE)

        database.movieDao().deleteMovie()

        database.movieDao().loadAllMovie()
            .test()
            .assertValue{it.isEmpty()}
    }

    companion object {
        private val MOVIE =
            Movie(id = 25, voteAverage = 7.21F, title = "Hoho", posterPath = "1", overview = "1", releaseDate = "1")
    }
}