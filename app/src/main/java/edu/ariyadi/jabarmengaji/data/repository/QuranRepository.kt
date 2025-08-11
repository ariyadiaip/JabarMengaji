package edu.ariyadi.jabarmengaji.data.repository

import android.util.Log
import edu.ariyadi.jabarmengaji.data.dao.QuranDao
import edu.ariyadi.jabarmengaji.data.model.DetailSurah
import edu.ariyadi.jabarmengaji.data.model.Surah
import edu.ariyadi.jabarmengaji.data.network.QuranApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuranRepository @Inject constructor(
    private val quranApiService: QuranApiService,
    private val quranDao: QuranDao
) {

    suspend fun refreshSurahList() {
        try {
            val response = quranApiService.getAllSurah()
            if (response.code == 200) {
                quranDao.insertAllSurah(response.data)
            }
        } catch (e: Exception) {
            Log.e("QuranRepository", "Error refreshing surah list: ${e.message}")
        }
    }

    fun getAllSurah(): Flow<List<Surah>> {
        return quranDao.getAllSurah()
    }

    suspend fun getSurahCount(): Int {
        return quranDao.getSurahCount()
    }

    fun searchSurah(query: String): Flow<List<Surah>> {
        return quranDao.searchSurah("%$query%")
    }

    suspend fun getDetailSurah(surahNumber: Int): DetailSurah? {
        val detailSurahFromDb = quranDao.getDetailSurahByNumber(surahNumber)

        if (detailSurahFromDb != null) {
            return detailSurahFromDb
        }

        return try {
            val response = quranApiService.getDetailSurah(surahNumber)
            if (response.code == 200) {
                quranDao.insertDetailSurah(response.data)
                response.data
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("QuranRepository", "Error getting detail surah: ${e.message}")
            null
        }
    }
}