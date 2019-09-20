package com.saucefan.stuff.readinglist

import android.app.Application
import com.saucefan.stuff.readinglist.room.BookDBRepo
import com.saucefan.stuff.readinglist.viewmodel.StorageInterface
import timber.log.Timber

val repo: StorageInterface by lazy {
    ReadingList.repo!!
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

class ReadingList : Application() {
    companion object {
      //  var prefs: SharedPrefsDao? = null
       // var localFiles: LocalFiles? =null
      var repo: StorageInterface? = null
    }

    override fun onCreate() {
        super.onCreate()

      //  prefs = SharedPrefsDao(applicationContext)
        repo = BookDBRepo(applicationContext)
        if (BuildConfig.DEBUG) {
            Timber.plant(MyDebugTree())
        }
    }
}
