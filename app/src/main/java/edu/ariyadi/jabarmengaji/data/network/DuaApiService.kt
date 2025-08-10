package edu.ariyadi.jabarmengaji.data.network

import edu.ariyadi.jabarmengaji.data.model.DuaResponse
import retrofit2.http.GET

interface DuaApiService {
    @GET("doa")
    suspend fun getAllDua(): DuaResponse
}