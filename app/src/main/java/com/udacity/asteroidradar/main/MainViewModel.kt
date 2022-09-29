package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidAPIFilter
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidDatabase.getInstance(application)
    private val repository = AsteroidRepository(database)

    private val asteroidsFilter = MutableLiveData<AsteroidAPIFilter>(AsteroidAPIFilter.SHOW_ALL)
    var asteroids: LiveData<List<Asteroid>> =
        Transformations.switchMap(asteroidsFilter) {
            when(it) {
                AsteroidAPIFilter.SHOW_TODAY -> repository.todayAsteroids
                AsteroidAPIFilter.SHOW_ALL -> repository.weekAsteroids
                else -> repository.asteroids
            }
        }

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    private val _dailyPicture = MutableLiveData<PictureOfDay>()
    val dailyPicture: LiveData<PictureOfDay>
        get() = _dailyPicture

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
                repository.refreshAsteroids()
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

    fun updateFilter(filter: AsteroidAPIFilter) {
        asteroidsFilter.value = filter
    }
}

