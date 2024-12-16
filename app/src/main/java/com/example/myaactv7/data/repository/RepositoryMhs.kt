package com.example.myaactv7.data.repository

import com.example.myaactv7.data.entity.Mahasiswa

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa : Mahasiswa)
}