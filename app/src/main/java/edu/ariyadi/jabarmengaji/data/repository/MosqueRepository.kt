package edu.ariyadi.jabarmengaji.data.repository

import edu.ariyadi.jabarmengaji.data.model.PlacesResponse
import edu.ariyadi.jabarmengaji.data.network.PlacesApiService
import javax.inject.Inject

class MosqueRepository @Inject constructor(
    private val placesApiService: PlacesApiService
) {
    suspend fun getNearbyMosques(latitude: Double, longitude: Double): PlacesResponse {
        return placesApiService.searchNearbyPlaces(
            location = "$latitude,$longitude",
            radius = 1500, // 1.5 km
            keyword = "masjid",
            apiKey = "YOUR_API_KEY"
        )
    }
}