package com.example.martinzamarski.moviedatabasekotlin.ui.detailactivity

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.martinzamarski.moviedatabasekotlin.databinding.ReviewsListBinding
import com.example.martinzamarski.moviedatabasekotlin.model.Reviews

class ReviewsAdapter(private val mReviews: List<Reviews>)
    : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ReviewsListBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mReviews[position])
    }

    override fun getItemCount(): Int = mReviews.size


    inner class ViewHolder(private val binding: ReviewsListBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(reviews: Reviews){
            binding.reviews = reviews
            binding.executePendingBindings()
        }
    }
}