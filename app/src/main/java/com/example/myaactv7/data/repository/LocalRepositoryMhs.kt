package com.example.myaactv7.data.repository

import com.example.myaactv7.data.dao.MahasiswaDao
import com.example.myaactv7.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMhs(
    private val MahasiswaDao: MahasiswaDao

) : RepositoryMhs{
    override suspend fun insertMhs(mahasiswa: Mahasiswa){
        MahasiswaDao.insertMahasiswa(mahasiswa)
    }

    override fun getAllMhs(): Flow<List<Mahasiswa>> {
        return MahasiswaDao.getAllMahasiswa()
    }

    override fun getMhs(nim: String): Flow<Mahasiswa>{
        return MahasiswaDao.getMahasiswa(nim)
    }

    override suspend fun deleteMhs(mahasiswa: Mahasiswa) {
        return MahasiswaDao.deleteMahasiswa(mahasiswa)
    }

    override suspend fun updateMhs(mahasiswa: Mahasiswa) {
        return MahasiswaDao.updateMahasiswa(mahasiswa)
    }
}