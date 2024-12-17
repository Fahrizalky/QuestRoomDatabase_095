package com.example.myaactv7.data.repository

import com.example.myaactv7.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa : Mahasiswa)
    suspend fun deleteMhs(mahasiswa: Mahasiswa)
    fun getMhs(nim: String): Flow<Mahasiswa>
    fun getAllMhs(): Flow<List<Mahasiswa>>
    suspend fun updateMhs(mahasiswa: Mahasiswa)
}