package edu.ariyadi.jabarmengaji.data.repository

import edu.ariyadi.jabarmengaji.data.dao.LastCityDao
import edu.ariyadi.jabarmengaji.data.model.LastCityEntity
import edu.ariyadi.jabarmengaji.data.model.SholatResponse
import edu.ariyadi.jabarmengaji.data.network.SholatApiService
import javax.inject.Inject

class SholatRepository @Inject constructor(
    private val apiService: SholatApiService,
    private val lastCityDao: LastCityDao
) {
    suspend fun getSholatSchedule(cityId: String, year: String, month: String, day: String): SholatResponse {
        return apiService.getSholatSchedule(cityId, year, month, day)
    }

    suspend fun saveLastCity(cityId: String) {
        lastCityDao.saveLastCity(LastCityEntity(cityId = cityId))
    }

    suspend fun getLastCityId(): String? {
        return lastCityDao.getLastCityId()
    }
}