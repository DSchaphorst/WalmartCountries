package com.example.dschaphorst_walmart_countries.network

import com.example.dschaphorst_walmart_countries.model.Countries
import retrofit2.Response
import retrofit2.http.GET

interface ApiHelper {

    @GET(PATH)
    suspend fun getCountries(): Response<Countries>

    companion object {
        const val BASE_URL = "https://gist.githubusercontent.com/peymano-wmt/"
        private const val PATH = "32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json"

        val serviceApi: ApiHelper = ApiService.countriesService
    }
}