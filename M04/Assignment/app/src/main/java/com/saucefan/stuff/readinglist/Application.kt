package com.saucefan.stuff.readinglist

import android.app.Application
import com.saucefan.stuff.readinglist.room.BookDBRepo
import com.saucefan.stuff.readinglist.viewmodel.LocalFiles
import com.saucefan.stuff.readinglist.viewmodel.SharedPrefsDao
import com.saucefan.stuff.readinglist.viewmodel.StorageInterface
import timber.log.Timber

val repo: StorageInterface by lazy {
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
      //  var prefs: SharedPrefsDao? = null
       // var localFiles: LocalFiles? =null
      var repo: StorageInterface? = null
    }

    override fun onCreate() {
        super.onCreate()
        repo = BookDBRepo(applicationContext)
      //  prefs = SharedPrefsDao(applicationContext)

        if (BuildConfig.DEBUG) {
            Timber.plant(MyDebugTree())
        }
    }
}
