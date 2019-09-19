package com.saucefan.stuff.readinglist

import android.app.Application
import com.saucefan.stuff.readinglist.viewmodel.LocalFiles
import com.saucefan.stuff.readinglist.viewmodel.SharedPrefsDao
import timber.log.Timber


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
      //  var prefs: SharedPrefsDao? = null
        var localFiles: LocalFiles? =null
    }

    override fun onCreate() {
        super.onCreate()
      //  prefs = SharedPrefsDao(applicationContext)

        if (BuildConfig.DEBUG) {
            Timber.plant(MyDebugTree())
        }
    }
}
