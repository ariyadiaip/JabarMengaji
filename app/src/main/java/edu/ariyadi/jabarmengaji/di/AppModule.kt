package edu.ariyadi.jabarmengaji.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ariyadi.jabarmengaji.data.dao.DuaDao
import edu.ariyadi.jabarmengaji.data.dao.LastCityDao
import edu.ariyadi.jabarmengaji.data.dao.QuranDao
import edu.ariyadi.jabarmengaji.data.database.AppDatabase
import edu.ariyadi.jabarmengaji.data.network.DuaApiService
import edu.ariyadi.jabarmengaji.data.network.QuranApiService
import edu.ariyadi.jabarmengaji.data.network.SholatApiService
import edu.ariyadi.jabarmengaji.data.repository.DuaRepository
import edu.ariyadi.jabarmengaji.data.repository.QuranRepository
import edu.ariyadi.jabarmengaji.data.repository.SholatRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "jabar_mengaji_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideLastCityDao(database: AppDatabase): LastCityDao {
        return database.lastCityDao()
    }

    @Provides
    @Singleton
    fun provideDuaDao(database: AppDatabase): DuaDao {
        return database.duaDao()
    }

    @Provides
    @Singleton
    fun provideQuranDao(database: AppDatabase): QuranDao {
        return database.quranDao()
    }

    @Provides
    @Singleton
    fun provideSholatRepository(
        sholatApiService: SholatApiService,
        lastCityDao: LastCityDao
    ): SholatRepository {
        return SholatRepository(sholatApiService, lastCityDao)
    }

    @Provides
    @Singleton
    fun provideDuaRepository(
        duaApiService: DuaApiService,
        duaDao: DuaDao
    ): DuaRepository {
        return DuaRepository(duaApiService, duaDao)
    }

    @Provides
    @Singleton
    fun provideQuranRepository(
        quranApiService: QuranApiService,
        quranDao: QuranDao
    ): QuranRepository {
        return QuranRepository(quranApiService, quranDao)
    }

}