package com.udacity.asteroidradar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.AsteroidAPIFilter
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.getSeventhDayFormatted
import com.udacity.asteroidradar.api.getTodayFormatted
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asAsteroidEntities
import com.udacity.asteroidradar.database.asAsteroids
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AsteroidRepository (private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAllSavedAsteroids()) {
            it.asAsteroids()
        }

    val todayAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(
            database.asteroidDao.getAsteroidwithDates(getTodayFormatted(), getTodayFormatted())
        ) {
            it.asAsteroids()
        }

    val weekAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(
            database.asteroidDao.getAsteroidwithDates(
                getTodayFormatted(),
                getSeventhDayFormatted()
            )
        ) {
            it.asAsteroids()
        }


    suspend fun refreshAsteroids() {
         withContext(Dispatchers.IO) {
                val asteroids = AsteroidApi.getAsteroids(
                    getTodayFormatted(), getSeventhDayFormatted()
                )
                database.asteroidDao.insertAllAsteroids(asteroids.asAsteroidEntities())

         }
    }

    suspend fun getDailyPicture(): PictureOfDay {
        return withContext(Dispatchers.IO) {
            AsteroidApi.getDailyPicture()
        }
    }
}