package edu.ariyadi.jabarmengaji.data.network

import edu.ariyadi.jabarmengaji.data.model.SholatResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SholatApiService {
    @GET("sholat/jadwal/{idKota}/{tahun}/{bulan}/{tanggal}")
    suspend fun getSholatSchedule(
        @Path("idKota") idKota: String,
        @Path("tahun") tahun: String,
        @Path("bulan") bulan: String,
        @Path("tanggal") tanggal: String
    ): SholatResponse
}
