package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.DatabaseConstants

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME} ORDER by closeApproachDate")
    fun getAllSavedAsteroids(): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME} WHERE closeApproachDate >= :starDate AND closeApproachDate <= :endDate" +
            " ORDER by closeApproachDate")
    fun getAsteroidwithDates(starDate: String, endDate: String): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(asteroids: List<AsteroidEntity>)

}