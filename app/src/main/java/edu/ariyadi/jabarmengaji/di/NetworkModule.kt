package edu.ariyadi.jabarmengaji.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ariyadi.jabarmengaji.data.network.DuaApiService
import edu.ariyadi.jabarmengaji.data.network.QuranApiService
import edu.ariyadi.jabarmengaji.data.network.SholatApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("MyQuranRetrofit")
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.myquran.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSholatApiService(@Named("MyQuranRetrofit") retrofit: Retrofit): SholatApiService {
        return retrofit.create(SholatApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("EQuranRetrofit")
    fun provideDuaRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://equran.id/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideDuaApiService(@Named("EQuranRetrofit") retrofit: Retrofit): DuaApiService {
        return retrofit.create(DuaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuranApiService(@Named("EQuranRetrofit") retrofit: Retrofit): QuranApiService {
        return retrofit.create(QuranApiService::class.java)
    }

}