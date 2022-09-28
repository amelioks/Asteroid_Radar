package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidApiService {
    @GET(Constants.HTTP_GET_NEO_FEED_PATH)
    suspend fun getAsteroidsApi(
        @Query(Constants.QUERY_START_DATE) startDate: String,
        @Query(Constants.QUERY_END_DATE) endDate: String,
        @Query(Constants.QUERY_API_KEY) apiKey: String): String

    @GET(Constants.HTTP_GET_APOD_PATH)
    suspend fun getPictureDay(
        @Query(Constants.QUERY_API_KEY) apiKey: String): PictureOfDay

}