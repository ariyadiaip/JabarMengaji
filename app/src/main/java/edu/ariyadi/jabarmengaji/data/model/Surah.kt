package edu.ariyadi.jabarmengaji.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(tableName = "surah_list")
@TypeConverters(SurahConverters::class)
data class Surah(
    @PrimaryKey
    @SerializedName("nomor")
    val nomor: Int,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("namaLatin")
    val namaLatin: String,
    @SerializedName("jumlahAyat")
    val jumlahAyat: Int,
    @SerializedName("tempatTurun")
    val tempatTurun: String,
    @SerializedName("arti")
    val arti: String,
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("audioFull")
    val audioFull: Map<String, String>
)

class SurahListResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Surah>
)

class SurahConverters {
    @TypeConverter
    fun fromString(value: String?): Map<String, String>? {
        val mapType: Type = object : TypeToken<Map<String, String>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromMap(map: Map<String, String>?): String? {
        return Gson().toJson(map)
    }
}