package com.example.martinzamarski.moviedatabasekotlin.ui.detailactivity

import com.example.martinzamarski.moviedatabasekotlin.data.MovieRepository
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import com.example.martinzamarski.moviedatabasekotlin.model.Reviews
import com.example.martinzamarski.moviedatabasekotlin.util.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

import org.mockito.MockitoAnnotations


@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @Mock
    private lateinit var movieRepository: MovieRepository

    private var schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailViewModel = DetailViewModel(movieRepository, schedulerProvider)
    }

    @Test
    fun getReviews() {
        val listReviews = listOf(REVIEWS)

        Mockito.`when`(movieRepository.getReviews(1)).thenReturn(Observable.just(listReviews))

        var testObservable = TestObserver<List<Reviews>>()
        detailViewModel.getReviews(1)
            .subscribe(testObservable)

        testObservable.assertNoErrors()
        testObservable.assertValue {it -> it[0].author == "tom"}

    }

    companion object {
       private val REVIEWS = Reviews(author = "tom", content = "nice", id = "1", url = "1")
    }
}