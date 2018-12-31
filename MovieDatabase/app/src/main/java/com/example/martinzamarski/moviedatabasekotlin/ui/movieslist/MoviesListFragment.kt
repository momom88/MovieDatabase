package com.example.martinzamarski.moviedatabasekotlin.ui.movieslist

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.martinzamarski.moviedatabasekotlin.R
import com.example.martinzamarski.moviedatabasekotlin.databinding.FragmentMoviesListBinding
import com.example.martinzamarski.moviedatabasekotlin.di.Injectable
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import com.example.martinzamarski.moviedatabasekotlin.ui.MovieInterface
import com.example.martinzamarski.moviedatabasekotlin.util.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class MoviesListFragment : Fragment(), MovieInterface, Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mBinding by autoCleared<FragmentMoviesListBinding>()

    private var mAdapter by autoCleared<MoviesListAdapter>()

    private val compositeDisposable by lazy { CompositeDisposable() }

    private lateinit var moviesListViewModel: MoviesListViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_movies_list, container, false
        )
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.app_name)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        moviesListViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(MoviesListViewModel::class.java)
        landOrPortable()
        setHasOptionsMenu(true)
        sortOrderMovie()
    }

    private fun setupViewModel(sortOrder: String) {
        compositeDisposable.add(
            moviesListViewModel.getMovie(sortOrder)
                .subscribeBy(
                    onNext = {
                        mBinding.loadingIndicator.visibility = View.GONE
                        mBinding.recyclerView.visibility = View.VISIBLE
                        Log.e("setupViewModel", "movieResult")
                        mAdapter = MoviesListAdapter(this, it)
                        mBinding.recyclerView.adapter = mAdapter
                    },
                    onError = {
                        mBinding.loadingIndicator.visibility = View.GONE
                        Log.e("setupViewModel", "movieError")
                        Toast.makeText(
                            context, resources.getString(R.string.movie_error_message) + it,
                            Toast.LENGTH_SHORT
                        ).show()
                    })
        )
    }

    private fun landOrPortable() {
//         Configuration Landscape or Portable
        val row = resources.getInteger(R.integer.row)
        mBinding.recyclerView.layoutManager = GridLayoutManager(context, row)
    }

    override fun onClick(movie: Movie) {
        Log.i("test", "onClick " + movie.title)
        val args = Bundle()
        args.putParcelable(MOVIE_ID, movie)
        navController().navigate(R.id.detailMovieFragment, args)
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.action_popular -> {
                sharedPreferencesSave(MOVIE_POPULAR)
                setupViewModel(MOVIE_POPULAR)
                return true
            }
            item.itemId == R.id.action_top_rated -> {
                sharedPreferencesSave(MOVIE_TOP_RATED)
                setupViewModel(MOVIE_TOP_RATED)
                return true
            }
            item.itemId == R.id.action_favorite -> {
                sharedPreferencesSave(MOVIE_FAVORITE)
                setupViewModel(MOVIE_FAVORITE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sharedPreferencesSave(sortOrder: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(SORT_ORDER_KEY, sortOrder)
            apply()
        }
    }

    private fun sortOrderMovie() {
        val sharePref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val sortOrder = sharePref.getString(SORT_ORDER_KEY, "")
        when (sortOrder) {
            MOVIE_POPULAR -> setupViewModel(sortOrder)
            MOVIE_TOP_RATED -> setupViewModel(sortOrder)
            MOVIE_FAVORITE -> setupViewModel(sortOrder)
            else -> setupViewModel(MOVIE_TOP_RATED)
        }
    }

    /**
     * Created to be able to override in tests
     */
    fun navController() = findNavController()
}
