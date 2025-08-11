package edu.ariyadi.jabarmengaji.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ariyadi.jabarmengaji.data.dao.DuaDao
import edu.ariyadi.jabarmengaji.data.dao.LastCityDao
import edu.ariyadi.jabarmengaji.data.dao.QuranDao
import edu.ariyadi.jabarmengaji.data.model.AyatConverters
import edu.ariyadi.jabarmengaji.data.model.DetailSurah
import edu.ariyadi.jabarmengaji.data.model.DuaConverters
import edu.ariyadi.jabarmengaji.data.model.DuaEntity
import edu.ariyadi.jabarmengaji.data.model.LastCityEntity
import edu.ariyadi.jabarmengaji.data.model.Surah
import edu.ariyadi.jabarmengaji.data.model.SurahConverters

@Database(entities = [LastCityEntity::class, DuaEntity::class, Surah::class, DetailSurah::class],
    version = 3,
    exportSchema = false)
@TypeConverters(DuaConverters::class, SurahConverters::class, AyatConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun lastCityDao(): LastCityDao

    abstract fun duaDao(): DuaDao

    abstract fun quranDao(): QuranDao

}