package edu.ariyadi.jabarmengaji.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ariyadi.jabarmengaji.data.model.LastCityEntity

@Dao
interface LastCityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLastCity(lastCity: LastCityEntity)

    @Query("SELECT cityId FROM last_city WHERE id = '1'")
    suspend fun getLastCityId(): String?
}