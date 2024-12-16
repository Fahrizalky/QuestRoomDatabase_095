package com.example.myaactv7

import android.app.Application
import com.example.myaactv7.dependenciesinjection.ContainerApp

class KrsApp: Application() {
    lateinit var containerApp: ContainerApp

    override fun  onCreate(){
        super.onCreate()

        containerApp = ContainerApp(this)
    }
}