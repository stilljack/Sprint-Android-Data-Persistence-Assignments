package com.lambdaschool.sharedprefs

import android.app.Application
import timber.log.Timber

// TODO 3: Notice that we are still using the interface we created
val repo: JournalRepoInterface by lazy {
    App.repo!!
}

class MyDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return String.format(
            "[C:%s] [M:%s] [L:%s]",
            super.createStackElementTag(element),
            element.methodName,
            element.lineNumber
        )
    }
}

class App : Application() {

    companion object {
        var repo: JournalRepoInterface? = null
    }

    override fun onCreate() {
        super.onCreate()

        //repo = Prefs(applicationContext)
        repo = JournalFileRepo(applicationContext)

        // TODO 18: Instantiate the DB repo here instead

        // "Timber" Library
        if (BuildConfig.DEBUG) {
            Timber.plant(MyDebugTree())
        }
    }
}