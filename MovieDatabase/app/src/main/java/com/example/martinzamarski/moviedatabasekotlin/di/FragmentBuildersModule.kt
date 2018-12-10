package com.example.martinzamarski.moviedatabasekotlin.di

import com.example.martinzamarski.moviedatabasekotlin.ui.about.AboutFragment
import com.example.martinzamarski.moviedatabasekotlin.ui.detailactivity.DetailFragment
import com.example.martinzamarski.moviedatabasekotlin.ui.movieslist.MoviesListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MoviesListFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment

    @ContributesAndroidInjector
    abstract fun contributeAboutFragment(): AboutFragment

}
