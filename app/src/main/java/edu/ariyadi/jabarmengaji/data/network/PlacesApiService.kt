package edu.ariyadi.jabarmengaji.data.network

import edu.ariyadi.jabarmengaji.data.model.PlacesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApiService {
    @GET("json")
    suspend fun searchNearbyPlaces(
        @Query("location") location: String, // format: "lat,lng"
        @Query("radius") radius: Int,
        @Query("keyword") keyword: String,
        @Query("key") apiKey: String
    ): PlacesResponse
}