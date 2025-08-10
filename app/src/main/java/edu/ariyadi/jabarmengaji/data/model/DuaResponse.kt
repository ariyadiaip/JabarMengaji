package edu.ariyadi.jabarmengaji.data.model

import com.google.gson.annotations.SerializedName

data class DuaResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("total")
    val total: Int,
    @SerializedName("data")
    val data: List<Dua>
)

data class Dua(
    @SerializedName("id")
    val id: Int,
    @SerializedName("grup")
    val grup: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("ar")
    val ar: String,
    @SerializedName("tr")
    val tr: String,
    @SerializedName("idn")
    val idn: String,
    @SerializedName("tentang")
    val tentang: String,
    @SerializedName("tag")
    val tag: List<String>
)