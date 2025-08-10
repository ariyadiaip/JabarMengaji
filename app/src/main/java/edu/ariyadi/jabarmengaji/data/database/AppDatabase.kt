package edu.ariyadi.jabarmengaji.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ariyadi.jabarmengaji.data.dao.DuaDao
import edu.ariyadi.jabarmengaji.data.dao.LastCityDao
import edu.ariyadi.jabarmengaji.data.model.DuaConverters
import edu.ariyadi.jabarmengaji.data.model.DuaEntity
import edu.ariyadi.jabarmengaji.data.model.LastCityEntity

@Database(entities = [LastCityEntity::class, DuaEntity::class], version = 2, exportSchema = false)
@TypeConverters(DuaConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun lastCityDao(): LastCityDao

    abstract fun duaDao(): DuaDao

}