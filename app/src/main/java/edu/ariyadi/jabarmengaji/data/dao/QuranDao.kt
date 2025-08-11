package edu.ariyadi.jabarmengaji.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ariyadi.jabarmengaji.data.model.DetailSurah
import edu.ariyadi.jabarmengaji.data.model.Surah
import kotlinx.coroutines.flow.Flow

@Dao
interface QuranDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSurah(surahList: List<Surah>)

    @Query("SELECT * FROM surah_list")
    fun getAllSurah(): Flow<List<Surah>>

    @Query("SELECT COUNT(*) FROM surah_list")
    suspend fun getSurahCount(): Int

    @Query("SELECT * FROM surah_list WHERE namaLatin LIKE :searchQuery OR arti LIKE :searchQuery")
    fun searchSurah(searchQuery: String): Flow<List<Surah>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailSurah(detailSurah: DetailSurah)

    @Query("SELECT * FROM detail_surah WHERE nomor = :surahNumber")
    suspend fun getDetailSurahByNumber(surahNumber: Int): DetailSurah?

}