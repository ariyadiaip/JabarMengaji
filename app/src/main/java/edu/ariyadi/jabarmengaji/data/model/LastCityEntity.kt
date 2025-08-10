package edu.ariyadi.jabarmengaji.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_city")
data class LastCityEntity(
    @PrimaryKey
    val id: String = "1",
    val cityId: String
)