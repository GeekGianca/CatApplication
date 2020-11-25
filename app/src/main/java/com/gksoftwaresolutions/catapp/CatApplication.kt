package com.gksoftwaresolutions.catapp

import android.app.Application
import com.gksoftwaresolutions.catapp.component.CatComponent
import com.gksoftwaresolutions.catapp.component.DaggerCatComponent
import com.gksoftwaresolutions.catapp.component.ViewModelFactory
import com.gksoftwaresolutions.catapp.data.local.LocalDatabase

class CatApplication : Application() {

    lateinit var catComponent: CatComponent

    override fun onCreate() {
        super.onCreate()
        catComponent = DaggerCatComponent
            .builder()
            .viewModelFactory(ViewModelFactory(this, LocalDatabase.getDatabase(this)))
            .build()
    }
}