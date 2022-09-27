package com.udacity.asteroidradar

object Constants {
    const val API_QUERY_DATE_FORMAT = "yyyy-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val API_KEY = "uYpqFd8crRtVVObZ2G3viAwyI0o74rwUCNZTXarv"

    const val QUERY_START_DATE = "start_date"
    const val QUERY_END_DATE = "end_date"

    const val HTTP_GET_NEO_FEED_PATH = "neo/rest/v1/feed"
    const val HTTP_GET_APOD_PATH = "planetary/apod"

}

object DatabaseConstants {
    const val TABLE_NAME = "asteroid"
    const val DATABASE_NAME = "asteroid_database"
}