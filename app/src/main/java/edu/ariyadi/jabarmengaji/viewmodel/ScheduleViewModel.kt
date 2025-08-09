package edu.ariyadi.jabarmengaji.viewmodel

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ariyadi.jabarmengaji.data.model.Jadwal
import edu.ariyadi.jabarmengaji.data.model.Lokasi
import edu.ariyadi.jabarmengaji.data.repository.SholatRepository
import edu.ariyadi.jabarmengaji.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: SholatRepository
) : ViewModel() {

    private val _jadwal = MutableStateFlow<Jadwal?>(null)
    val jadwal: StateFlow<Jadwal?> = _jadwal

    private val _selectedLocation = MutableStateFlow<Lokasi?>(null)
    val selectedLocation: StateFlow<Lokasi?> = _selectedLocation

    private val _currentNextPrayerInfo = MutableStateFlow<Pair<String, String>?>(null)
    val currentNextPrayerInfo: StateFlow<Pair<String, String>?> = _currentNextPrayerInfo

    private val _nextPrayerName = MutableStateFlow<String?>(null)
    val nextPrayerName: StateFlow<String?> = _nextPrayerName

    init {
        loadLastSelectedCity()
    }

    private fun loadLastSelectedCity() {
        viewModelScope.launch {
            val lastCityId = repository.getLastCityId() ?: Constants.DEFAULT_LOCATION_ID
            val selectedCity = Constants.JABAR_LOCATIONS.find { it.id == lastCityId }
            _selectedLocation.value = selectedCity
            fetchSholatSchedule(lastCityId)
        }
    }

    fun fetchSholatSchedule(cityId: String) {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR).toString()
            val month = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
            val day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))

            try {
                val response = repository.getSholatSchedule(cityId, year, month, day)
                _jadwal.value = response.data.jadwal
                repository.saveLastCity(cityId)
            } catch (e: Exception) {
                Log.e("ScheduleViewModel", "Error fetching sholat schedule: ${e.message}")
            }
        }
    }

    @SuppressLint("NewApi")
    fun calculateNextPrayerTime(jadwal: Jadwal) {
        val prayerTimes = mapOf(
            "Imsak" to jadwal.imsak,
            "Subuh" to jadwal.subuh,
            "Terbit" to jadwal.terbit,
            "Dzuhur" to jadwal.dzuhur,
            "Ashar" to jadwal.ashar,
            "Maghrib" to jadwal.maghrib,
            "Isya" to jadwal.isya
        )

        val now = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")

        var nextPrayer: Pair<String, String>? = null

        for ((prayerName, timeString) in prayerTimes) {
            val prayerTime = LocalTime.parse(timeString, formatter)
            if (prayerTime.isAfter(now)) {
                nextPrayer = Pair(prayerName, timeString)
                _nextPrayerName.value = prayerName
                break
            }
        }

        if (nextPrayer == null) {
            _nextPrayerName.value = "Imsak"
            _currentNextPrayerInfo.value = Pair("Waktu Isya Sudah Lewat", "Beberapa waktu yang lalu")
            return
        }

        // Apakah ada waktu sholat yang baru saja terlewat (dalam 1 jam terakhir)
        var lastPrayerPassed: Pair<String, String>? = null
        for ((prayerName, timeString) in prayerTimes.asIterable().reversed()) {
            val prayerTime = LocalTime.parse(timeString, formatter)
            val diffInMinutes = ChronoUnit.MINUTES.between(prayerTime, now)
            if (diffInMinutes > 0 && diffInMinutes <= 59) {
                lastPrayerPassed = Pair(prayerName, "± $diffInMinutes menit yang lalu")
                break
            }
        }

        if (lastPrayerPassed != null) {
            _currentNextPrayerInfo.value = Pair("Waktu ${lastPrayerPassed.first} Sudah Lewat", lastPrayerPassed.second)
        } else {
            //Waktu sholat yang akan datang
            val nextPrayerTime = LocalTime.parse(nextPrayer.second, formatter)
            val diffSeconds = ChronoUnit.SECONDS.between(now, nextPrayerTime)
            val hours = diffSeconds / 3600
            val minutes = (diffSeconds % 3600) / 60
            val timeString = if (hours > 0) {
                "± $hours jam $minutes menit lagi"
            } else {
                "± $minutes menit lagi"
            }
            _currentNextPrayerInfo.value = Pair("Waktu ${nextPrayer.first} Akan Tiba", timeString)
        }

    }

    fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return dateFormat.format(calendar.time)
    }

}