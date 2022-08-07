package me.ctossato.githubrepos

import android.app.Application
import me.ctossato.githubrepos.data.di.DataModule
import me.ctossato.githubrepos.domain.di.DomainModule
import me.ctossato.githubrepos.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModule.load()
        DomainModule.load()
        PresentationModule.load()
    }
}