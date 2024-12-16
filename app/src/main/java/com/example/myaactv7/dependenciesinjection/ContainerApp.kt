package com.example.myaactv7.dependenciesinjection

import android.content.Context
import com.example.myaactv7.data.database.krsDatabase
import com.example.myaactv7.data.repository.LocalRepositoryMhs
import com.example.myaactv7.data.repository.RepositoryMhs 


interface InterfaceContainerApp {
    val repositoryMhs:RepositoryMhs
}

class ContainerApp(private val context: Context):InterfaceContainerApp{
    override val  repositoryMhs: RepositoryMhs by lazy {
        LocalRepositoryMhs(krsDatabase.getDatabase(context).mahasiswaDao())
    }
}