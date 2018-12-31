package com.example.martinzamarski.moviedatabasekotlin.ui.movieslist


import com.example.martinzamarski.moviedatabasekotlin.data.MovieRepository
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import com.example.martinzamarski.moviedatabasekotlin.util.MOVIE_POPULAR
import com.example.martinzamarski.moviedatabasekotlin.util.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MoviesListViewModelTest {

    @Mock
    private lateinit var mockRepository: MovieRepository

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private lateinit var movieListViewModel: MoviesListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieListViewModel = MoviesListViewModel(mockRepository, schedulerProvider)
    }

    @Test
    fun getMovie() {
        val listMovie: List<Movie> = listOf(MOVIE)
        Mockito.`when`(mockRepository.getMovieList(MOVIE_POPULAR)).thenReturn(Observable.just(listMovie))

        val testObserver = TestObserver<List<Movie>>()
        movieListViewModel.getMovie(MOVIE_POPULAR)
            .subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue{ it -> it[0].id == 1 }
    }

    companion object {
        private val MOVIE = Movie(id = 1,voteAverage =  7.21F, title = "Hoho",posterPath =  "1",overview =  "1",releaseDate =  "1")
    }
}