package com.example.martinzamarski.moviedatabasekotlin.data

import com.example.martinzamarski.moviedatabasekotlin.data.api.ApiInterface
import com.example.martinzamarski.moviedatabasekotlin.data.database.MovieDao
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import com.example.martinzamarski.moviedatabasekotlin.model.MovieResponse
import com.example.martinzamarski.moviedatabasekotlin.model.Reviews
import com.example.martinzamarski.moviedatabasekotlin.model.ReviewsResponse
import com.example.martinzamarski.moviedatabasekotlin.util.MOVIE_POPULAR
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*

import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    @Mock
    private lateinit var apiInterface: ApiInterface
    @Mock
    private lateinit var movieDao: MovieDao

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieRepository = MovieRepository(apiInterface, movieDao)
    }

    @Test
    fun getMovieList() {
        val listMovie: List<Movie> = listOf(MOVIE)

        `when`(apiInterface.getMoviePopular(MOVIE_POPULAR)).thenReturn(Observable.just(MovieResponse(listMovie)))

        val testObserver = TestObserver<List<Movie>>()
        movieRepository.getMovieList(MOVIE_POPULAR)
            .subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue { it -> it[0].id == 1}
    }

    @Test
    fun getReviews() {
        val listReviews = listOf(REVIEWS)

        `when`(apiInterface.getReviews(1)).thenReturn(Observable.just(ReviewsResponse(listReviews)))

        val testObserver = TestObserver<List<Reviews>>()
        movieRepository.getReviews(1)
            .subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue { it -> it[0].author == "tom" }
    }

    companion object {
        private val MOVIE = Movie(id = 1,voteAverage =  7.21F, title = "Hoho",posterPath =  "1",overview =  "1",releaseDate =  "1")
        private val REVIEWS = Reviews(author = "tom", content = "nice", id = "1", url = "1")
    }
}