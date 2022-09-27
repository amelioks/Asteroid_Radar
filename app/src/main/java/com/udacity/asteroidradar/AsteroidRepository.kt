package com.udacity.asteroidradar

import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asAsteroidEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository (private val database: AsteroidDatabase) {


    suspend fun getAsteroids(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            val asteroids = AsteroidApi.getAsteroids()
            database.asteroidDao.insertAllAsteroids(asteroids.asAsteroidEntities())
            asteroids
        }
    }

    suspend fun getDailyPicture(): PictureOfDay {
        return withContext(Dispatchers.IO) {
            AsteroidApi.getDailyPicture()
        }
    }
}