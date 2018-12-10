package com.example.martinzamarski.moviedatabasekotlin

import android.os.Bundle

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.martinzamarski.moviedatabasekotlin.databinding.MainActivityBinding

import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)

        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.container)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        navController.addOnNavigatedListener{nc: NavController, nd: NavDestination ->
            if (nd.id == nc.graph.startDestination){
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }else{
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.container)
        return NavigationUI.navigateUp(drawerLayout, navController)
    }
}


