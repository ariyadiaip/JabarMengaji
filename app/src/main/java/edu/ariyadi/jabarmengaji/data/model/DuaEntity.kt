package edu.ariyadi.jabarmengaji.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(tableName = "dua_table")
@TypeConverters(DuaConverters::class)
data class DuaEntity(
    @PrimaryKey
    val id: Int,
    val grup: String,
    val nama: String,
    val ar: String,
    val tr: String,
    val idn: String,
    val tentang: String,
    val tag: List<String>
)

class DuaConverters {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return Gson().toJson(list)
    }
}