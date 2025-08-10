package edu.ariyadi.jabarmengaji.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ariyadi.jabarmengaji.data.model.DuaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DuaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDua(duaList: List<DuaEntity>)

    @Query("SELECT * FROM dua_table")
    fun getAllDua(): Flow<List<DuaEntity>>

    @Query("SELECT * FROM dua_table WHERE id = :duaId")
    suspend fun getDuaById(duaId: Int): DuaEntity?

    @Query("SELECT * FROM dua_table WHERE nama LIKE :searchQuery OR grup LIKE :searchQuery")
    fun searchDua(searchQuery: String): Flow<List<DuaEntity>>

    @Query("SELECT COUNT(*) FROM dua_table")
    suspend fun getDuaCount(): Int

}