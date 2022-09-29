package com.udacity.asteroidradar

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.udacity.asteroidradar.api.AsteroidAPIFilter
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asAsteroidEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AsteroidRepository (private val database: AsteroidDatabase) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAsteroids(filter: AsteroidAPIFilter): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            val formatter = DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT)

            val starDate = LocalDateTime.now()
            val endDate = starDate.plusDays(Constants.DEFAULT_END_DATE_DAYS.toLong())

            val startDateFormatted = starDate.format(formatter)
            val endDateFormatted = endDate.format(formatter)

            val asteroids = AsteroidApi.getAsteroids(startDateFormatted, endDateFormatted)
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