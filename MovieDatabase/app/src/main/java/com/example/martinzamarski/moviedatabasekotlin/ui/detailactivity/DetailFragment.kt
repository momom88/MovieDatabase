package com.example.martinzamarski.moviedatabasekotlin.ui.detailactivity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.martinzamarski.moviedatabasekotlin.R
import com.example.martinzamarski.moviedatabasekotlin.databinding.FragmentDetailBinding
import com.example.martinzamarski.moviedatabasekotlin.di.Injectable
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import com.example.martinzamarski.moviedatabasekotlin.model.Reviews
import com.example.martinzamarski.moviedatabasekotlin.ui.MovieInterface
import com.example.martinzamarski.moviedatabasekotlin.util.IMAGEURL
import com.example.martinzamarski.moviedatabasekotlin.util.MOVIE_ID
import com.example.martinzamarski.moviedatabasekotlin.util.autoCleared
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailFragment : Fragment(), MovieInterface, Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mBinding by autoCleared<FragmentDetailBinding>()

    private var mAdapter by autoCleared<ReviewsAdapter>()

    private val compositeDisposable by lazy { CompositeDisposable() }

    private lateinit var detailViewModel: DetailViewModel

    private lateinit var mMovie: Movie

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )
        mBinding.clickMovieFavorite = this
        Log.i("test", "DetailFragment")
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        mMovie = arguments!!.getParcelable(MOVIE_ID)
        detailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        mBinding.recyclerViewDetail.layoutManager = LinearLayoutManager(context)
        setupViewModel(mMovie)
        setMovieDataToBinding(mMovie)
    }

    private fun setupViewModel(movie: Movie) {
        compositeDisposable.add(
            detailViewModel.getReviews(movie.id)
                .subscribeBy(
                    onNext = {
                        mBinding.loadingIndicator.visibility = View.GONE
                        mAdapter = ReviewsAdapter(it)
                        mBinding.recyclerViewDetail.adapter = mAdapter
                    },
                    onError = {
                        mBinding.loadingIndicator.visibility = View.GONE
                        Toast.makeText(
                            context, resources.getString(R.string.movie_error_message) + it,
                            Toast.LENGTH_SHORT
                        ).show()
                    })
        )
    }

    //Click to save favorite button
    override fun onClick(movie: Movie) {
        compositeDisposable.add(
            detailViewModel.saveToFavorite(movie)
                .subscribeBy(
                    onSuccess = {
                        Toast.makeText(context, resources.getString(R.string.saved_to_favorites), Toast.LENGTH_LONG).show()
                    },
                    onError = {
                        Toast.makeText(context, resources.getString(R.string.notSaved_to_favorites), Toast.LENGTH_LONG).show()
                    }

                )
        )
    }

    private fun setMovieDataToBinding(movie: Movie) {
        (activity as AppCompatActivity).supportActionBar?.title = movie.title
        mBinding.movie = movie
        Picasso.get()
            .load(IMAGEURL + movie.posterPath)
            .into(mBinding.poster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}



