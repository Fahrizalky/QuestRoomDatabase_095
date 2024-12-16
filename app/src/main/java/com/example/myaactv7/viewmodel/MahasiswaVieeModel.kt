package com.example.myaactv7.viewmodel

import com.example.myaactv7.data.entity.Mahasiswa


fun MahasiswaEvent.toMahasiswaEntity(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    jenisKelamin = jenisKelmain,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan

)
data class MahasiswaEvent(
    val nim: String ="",
    val nama: String = "",
    val jenisKelmain: String ="",
    val alamat: String ="",
    val kelas: String ="",
    val angkatan: String =""
)