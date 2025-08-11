package edu.ariyadi.jabarmengaji.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(tableName = "detail_surah")
@TypeConverters(AyatConverters::class)
data class DetailSurah(
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
    val audioFull: Map<String, String>,
    @SerializedName("ayat")
    val ayat: List<Ayat>
)

data class Ayat(
    @SerializedName("nomorAyat")
    val nomorAyat: Int,
    @SerializedName("teksArab")
    val teksArab: String,
    @SerializedName("teksLatin")
    val teksLatin: String,
    @SerializedName("teksIndonesia")
    val teksIndonesia: String,
    @SerializedName("audio")
    val audio: Map<String, String>
)

class DetailSurahResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DetailSurah
)

class AyatConverters {
    @TypeConverter
    fun fromString(value: String?): List<Ayat>? {
        val listType: Type = object : TypeToken<List<Ayat>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Ayat>?): String? {
        return Gson().toJson(list)
    }
}