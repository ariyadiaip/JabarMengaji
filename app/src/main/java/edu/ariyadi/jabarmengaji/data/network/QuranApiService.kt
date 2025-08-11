package edu.ariyadi.jabarmengaji.data.network

import edu.ariyadi.jabarmengaji.data.model.DetailSurahResponse
import edu.ariyadi.jabarmengaji.data.model.SurahListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiService {

    @GET("v2/surat")
    suspend fun getAllSurah(): SurahListResponse

    @GET("v2/surat/{nomor_surat}")
    suspend fun getDetailSurah(@Path("nomor_surat") nomorSurat: Int): DetailSurahResponse

}