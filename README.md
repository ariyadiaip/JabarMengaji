# Jabar Mengaji 📱

**Jabar Mengaji** adalah aplikasi Android untuk membantu umat Muslim di Jawa Barat mengakses Al-Qur'an, doa sehari-hari, jadwal sholat, dan arah kiblat secara mudah dan interaktif.
Aplikasi ini dirancang dengan tampilan modern, ringan, dan ramah pengguna.

🔗 **Download**: [Jabar Mengaji di Itch.io](https://ariyadiaip.itch.io/jabar-mengaji)

> 🛠 Dibangun menggunakan **Android Studio Meerkat 2024.3.1**
> **Min SDK**: 24
> **Target SDK**: 35

---

## ✨ Fitur Utama

- 🕌 **Jadwal Sholat & Arah Kiblat**
  - Integrasi kompas kiblat
  - Lokasi otomatis dengan GPS
  - Jadwal sholat berdasarkan lokasi

- 📖 **Al-Qur'an Digital**
  - Pencarian surat
  - Detail surat (deskripsi, jumlah ayat, arti, audio)
  - Navigasi ayat per ayat

- 📍 **Masjid Terdekat**
  - Integrasi dengan Google Maps
  - Menampilkan lokasi masjid terdekat dari posisi pengguna

- 🙏 **Doa Sehari-hari**
  - Kumpulan doa dengan teks Arab, latin, dan terjemahan
  - Referensi hadits dan sumber

- 👥 **Komunitas**
  - List Kajian Masjid di wilayah Jawa Barat
  - Alamat dan detail kegiatan

---

## 🛠 Teknologi yang Digunakan

- **Kotlin** sebagai bahasa utama
- **MVVM Architecture** untuk struktur aplikasi
- **Room Database** untuk penyimpanan offline
- **Retrofit** untuk API request
- **ViewBinding** untuk mengelola UI
- **CompassQibla Library** untuk arah kiblat
- **MediaPlayer** untuk audio Qur'an
- **Material Design Components** untuk UI

---

## 🌐 API & Layanan Pihak Ketiga

- **[MyQuran API](https://documenter.getpostman.com/view/841292/2s9YsGittd)** → Jadwal Sholat
- **[EQuran API](https://equran.id/apidev)** → Data Al-Qur'an & Doa
- **Google Maps API** → Peta masjid terdekat & navigasi

---

## 📷 Screenshot

| Jadwal Sholat | Al-Quran | Komunitas |
|---------------|----------|-----------|
| ![Jadwal Sholat](screenshots/jadwal_sholat.png) | ![Al-Quran](screenshots/al_quran.png) | ![Komunitas](screenshots/komunitas.png) |

---

## 📦 Instalasi & Build

1. Clone repository ini:
   ```bash
   git clone https://github.com/ariyadiaip/JabarMengaji.git
