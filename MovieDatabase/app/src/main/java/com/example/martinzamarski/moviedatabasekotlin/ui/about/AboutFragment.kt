package com.example.martinzamarski.moviedatabasekotlin.ui.about


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.example.martinzamarski.moviedatabasekotlin.R
import com.example.martinzamarski.moviedatabasekotlin.databinding.FragmentAboutBinding
import com.example.martinzamarski.moviedatabasekotlin.di.Injectable
import com.example.martinzamarski.moviedatabasekotlin.util.autoCleared

/**
 * A simple [Fragment] subclass.
 *
 */
class AboutFragment : Fragment(), Injectable {

    private var mBinding by autoCleared<FragmentAboutBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_about, container, false
        )
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.app_name)
        return mBinding.root
    }
}
