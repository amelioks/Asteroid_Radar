package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidDatabase.getInstance(application)
    private val repository = AsteroidRepository(database)

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    private val _dailyPicture = MutableLiveData<PictureOfDay>()
    val dailyPicture: LiveData<PictureOfDay>
        get() = _dailyPicture

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids


    init {
        getAsteroids()
        getDailyPicture()

    }

    private fun getDailyPicture() {
        viewModelScope.launch {
            try {
                val data = repository.getDailyPicture()
                _dailyPicture.value = data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            try {
                val data = repository.getAsteroids()
                _asteroids.value = data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun onAsteroidClick(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun asteroidDetailNavigated() {
        _navigateToSelectedAsteroid.value = null
    }


}

