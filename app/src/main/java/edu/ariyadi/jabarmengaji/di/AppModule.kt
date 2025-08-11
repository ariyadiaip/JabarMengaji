package edu.ariyadi.jabarmengaji.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
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
import edu.ariyadi.jabarmengaji.data.network.PlacesApiService
import edu.ariyadi.jabarmengaji.data.network.QuranApiService
import edu.ariyadi.jabarmengaji.data.network.SholatApiService
import edu.ariyadi.jabarmengaji.data.repository.CommunityRepository
import edu.ariyadi.jabarmengaji.data.repository.DuaRepository
import edu.ariyadi.jabarmengaji.data.repository.MosqueRepository
import edu.ariyadi.jabarmengaji.data.repository.QuranRepository
import edu.ariyadi.jabarmengaji.data.repository.SholatRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideCommunityRepository(firestore: FirebaseFirestore): CommunityRepository {
        return CommunityRepository(firestore)
    }

    @Provides
    @Singleton
    @Named("PlacesRetrofit")
    fun providePlacesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePlacesApiService(@Named("PlacesRetrofit") retrofit: Retrofit): PlacesApiService {
        return retrofit.create(PlacesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMosqueRepository(placesApiService: PlacesApiService): MosqueRepository {
        return MosqueRepository(placesApiService)
    }

}