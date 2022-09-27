package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.udacity.asteroidradar.Asteroid
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.FragmentAsteroidListBinding
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class AsteroidAdapter(val onClickListener: OnClickListener )
    : ListAdapter<Asteroid,AsteroidAdapter.AsteroidViewHolder>(DiffCallback) {


    class AsteroidViewHolder(
        private val binding: FragmentAsteroidListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid, clickListener: OnClickListener) {
            binding.asteroidList = asteroid
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(FragmentAsteroidListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(asteroid)
        }

        holder.bind(asteroid, onClickListener)

    }

    class OnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}


