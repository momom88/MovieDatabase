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
        Log.i("test", "detailfragment" + mMovie.title)
        mBinding.recyclerViewDetail.layoutManager = LinearLayoutManager(context)
        setupViewModel(mMovie)
        setMovieDataToBinding(mMovie)
    }

    private fun setupViewModel(movie: Movie) {
        detailViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        detailViewModel.getReviews(movie).observe(this, Observer<List<Reviews>> { it ->
            it?.let {
                mAdapter = ReviewsAdapter(it)
                mBinding.recyclerViewDetail.adapter = mAdapter
            }
        })
    }

    private fun setMovieDataToBinding(movie: Movie) {
        (activity as AppCompatActivity).supportActionBar?.title = movie.title
        mBinding.movie = movie
        Picasso.get()
            .load(IMAGEURL + movie.posterPath)
            .into(mBinding.poster)
    }

    override fun onClick(movie: Movie) {
        Log.i("test" ,"on Click Saved")
          detailViewModel.setMovie(movie)
        Toast.makeText(
            context, resources.getString(R.string.saved_to_favorites),
            Toast.LENGTH_LONG
        ).show()
    }
}



