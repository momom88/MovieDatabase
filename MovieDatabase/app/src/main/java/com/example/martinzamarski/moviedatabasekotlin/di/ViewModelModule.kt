package com.example.martinzamarski.moviedatabasekotlin.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.martinzamarski.moviedatabasekotlin.ui.detailactivity.DetailViewModel
import com.example.martinzamarski.moviedatabasekotlin.ui.movieslist.MoviesListViewModel
import com.example.martinzamarski.moviedatabasekotlin.viewmodel.MovieViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesListViewModel::class)
    abstract fun bindMainViewModel(moviesListViewModel: MoviesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MovieViewModelFactory): ViewModelProvider.Factory
}