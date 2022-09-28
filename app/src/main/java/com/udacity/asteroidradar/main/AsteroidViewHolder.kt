package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidListBinding

class AsteroidViewHolder private constructor(private val binding: AsteroidListBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(asteroid: Asteroid, clickListener: AsteroidAdapter.OnClickListener) {
        binding.asteroid = asteroid
        binding.clickListener = clickListener
        binding.executePendingBindings()

    }

    companion object {
        fun createView(parent: ViewGroup): AsteroidViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AsteroidListBinding.inflate(layoutInflater, parent, false)
            return AsteroidViewHolder(binding)
        }
    }
}