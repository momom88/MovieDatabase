package com.example.martinzamarski.moviedatabasekotlin.ui.movieslist

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.martinzamarski.moviedatabasekotlin.R
import com.example.martinzamarski.moviedatabasekotlin.databinding.FragmentMoviesListBinding
import com.example.martinzamarski.moviedatabasekotlin.di.Injectable
import com.example.martinzamarski.moviedatabasekotlin.model.Movie
import com.example.martinzamarski.moviedatabasekotlin.ui.MovieInterface
import com.example.martinzamarski.moviedatabasekotlin.util.*
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
        loadingIndicator()
        landOrPortable()
        setHasOptionsMenu(true)
        sortOrderMovie()
    }

    private fun setupViewModel(sortOrder: Int) {
        moviesListViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(MoviesListViewModel::class.java)
        moviesListViewModel.getMovie(sortOrder).observe(this, Observer<List<Movie>> { it ->
            it?.let {
                mAdapter = MoviesListAdapter(this, it)
                mBinding.recyclerView.adapter = mAdapter
                loadData()
            }

        })
    }

    private fun loadingIndicator() {
        mBinding.loadingIndicator.visibility = View.VISIBLE
        mBinding.recyclerView.visibility = View.GONE
    }

    private fun loadData() {
        mBinding.loadingIndicator.visibility = View.GONE
        mBinding.recyclerView.visibility = View.VISIBLE
    }

    /**
     * set the number of the image in the row
     */
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

    /**
     * This method initialize the contents of the Activity's options menu.
     *
     * @param menu
     * @return
     */
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

    /**
     * This method of saving the selected order to share preference
     *
     * @param sortOrder
     */
    private fun sharedPreferencesSave(sortOrder: Int) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putInt(SORT_ORDER_KEY, sortOrder)
        editor.apply()
    }

    private fun sortOrderMovie() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val sortOrder = preferences.getInt(SORT_ORDER_KEY, -1)
        when (sortOrder) {
            MOVIE_POPULAR -> setupViewModel(sortOrder)
            MOVIE_TOP_RATED -> setupViewModel(sortOrder)
            //        }else if (sortOrder == MOVIE_FAVORITE){
            //            setupViewModel(sortOrder);
            else -> setupViewModel(MOVIE_TOP_RATED)
        }
    }

    /**
     * Created to be able to override in tests
     */
    fun navController() = findNavController()
}
