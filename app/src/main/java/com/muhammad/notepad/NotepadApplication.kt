package com.muhammad.notepad

import android.app.Application
import com.muhammad.notepad.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class NotepadApplication : Application(){
    companion object{
        lateinit var INSTANCE : NotepadApplication
    }
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@NotepadApplication)
            androidLogger()
            modules(appModule)
        }
    }
}