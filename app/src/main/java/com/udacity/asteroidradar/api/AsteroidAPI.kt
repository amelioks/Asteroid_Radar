package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi.retrofitService
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object AsteroidApi {
    //for picture of the day
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.BASE_URL)
        .build()

    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }


    suspend fun getAsteroids(): List<Asteroid> {
        val stringResult = retrofitService.getAsteroidsApi("2022-09-27","2022-10-04",Constants.API_KEY)
        val jsonObjectResult = JSONObject(stringResult)

        return parseAsteroidsJsonResult(jsonObjectResult)
    }

    suspend fun getDailyPicture(): PictureOfDay = retrofitService.getPictureDay(Constants.API_KEY)

}