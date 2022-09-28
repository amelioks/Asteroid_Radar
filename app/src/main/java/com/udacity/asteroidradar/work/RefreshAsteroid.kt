package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.database.AsteroidDatabase
import retrofit2.HttpException

class RefreshAsteroid (appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshAsteroidsWorker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.getAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}