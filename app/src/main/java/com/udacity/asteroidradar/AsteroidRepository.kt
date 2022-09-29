package com.udacity.asteroidradar

import android.os.Build
import androidx.annotation.RequiresApi
import com.udacity.asteroidradar.api.AsteroidAPIFilter
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.getSeventhDayFormatted
import com.udacity.asteroidradar.api.getTodayFormatted
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asAsteroidEntities
import com.udacity.asteroidradar.database.asAsteroids
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AsteroidRepository (private val database: AsteroidDatabase) {

    suspend fun getAsteroids(filter: AsteroidAPIFilter): List<Asteroid> {
        return withContext(Dispatchers.IO) {

            val asteroids = AsteroidApi.getAsteroids(
                getTodayFormatted(), getSeventhDayFormatted()
            )
            database.asteroidDao.insertAllAsteroids(asteroids.asAsteroidEntities())
            when (filter) {
                AsteroidAPIFilter.SHOW_TODAY -> getTodayAsteroids()
                AsteroidAPIFilter.SHOW_ALL -> getWeekAsteroids()
                else -> database.asteroidDao.getAllSavedAsteroids().asAsteroids()
            }
        }
    }

    suspend fun getWeekAsteroids(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            database.asteroidDao.getAsteroidwithDates(
                getTodayFormatted(), getSeventhDayFormatted()
            ).asAsteroids()
        }
    }

    suspend fun getTodayAsteroids(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            database.asteroidDao.getAsteroidwithDates(getTodayFormatted(),
                getSeventhDayFormatted()
            )
                .asAsteroids()
        }
    }

    suspend fun getDailyPicture(): PictureOfDay {
        return withContext(Dispatchers.IO) {
            AsteroidApi.getDailyPicture()
        }
    }
}