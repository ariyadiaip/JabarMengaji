package edu.ariyadi.jabarmengaji.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ariyadi.jabarmengaji.data.dao.LastCityDao
import edu.ariyadi.jabarmengaji.data.model.LastCity

@Database(entities = [LastCity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun lastCityDao(): LastCityDao

}