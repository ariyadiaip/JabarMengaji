package edu.ariyadi.jabarmengaji.data.model

import com.google.gson.annotations.SerializedName

data class SholatResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("data")
    val data: Data
)

data class Data(
    @SerializedName("id")
    val id: String,
    @SerializedName("lokasi")
    val lokasi: String,
    @SerializedName("daerah")
    val daerah: String,
    @SerializedName("jadwal")
    val jadwal: Jadwal
)

data class Jadwal(
    @SerializedName("tanggal")
    val tanggal: String,
    @SerializedName("imsak")
    val imsak: String,
    @SerializedName("subuh")
    val subuh: String,
    @SerializedName("terbit")
    val terbit: String,
    @SerializedName("dhuha")
    val dhuha: String,
    @SerializedName("dzuhur")
    val dzuhur: String,
    @SerializedName("ashar")
    val ashar: String,
    @SerializedName("maghrib")
    val maghrib: String,
    @SerializedName("isya")
    val isya: String,
    @SerializedName("date")
    val date: String
)