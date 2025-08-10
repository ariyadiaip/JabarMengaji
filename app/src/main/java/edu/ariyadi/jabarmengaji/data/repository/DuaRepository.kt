package edu.ariyadi.jabarmengaji.data.repository

import android.util.Log
import edu.ariyadi.jabarmengaji.data.dao.DuaDao
import edu.ariyadi.jabarmengaji.data.model.DuaEntity
import edu.ariyadi.jabarmengaji.data.network.DuaApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DuaRepository @Inject constructor(
    private val duaApiService: DuaApiService,
    private val duaDao: DuaDao
) {
    val allDua: Flow<List<DuaEntity>> = duaDao.getAllDua()

    suspend fun refreshDuaList() {
        try {
            val response = duaApiService.getAllDua()
            if (response.status == "success") {
                val duaEntities = response.data.map { dua ->
                    DuaEntity(
                        id = dua.id,
                        grup = dua.grup,
                        nama = dua.nama,
                        ar = dua.ar,
                        tr = dua.tr,
                        idn = dua.idn,
                        tentang = dua.tentang,
                        tag = dua.tag
                    )
                }
                duaDao.insertAllDua(duaEntities)
            }
        } catch (e: Exception) {
            Log.e("DuaRepository", "Error refreshing dua list: ${e.message}")
        }
    }

    fun searchDua(query: String): Flow<List<DuaEntity>> {
        return duaDao.searchDua("%$query%")
    }

    suspend fun getDuaCount(): Int {
        return duaDao.getDuaCount()
    }

    suspend fun getDuaById(id: Int): DuaEntity? {
        return duaDao.getDuaById(id)
    }
}