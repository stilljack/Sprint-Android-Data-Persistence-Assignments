package com.saucefan.stuff.sharedprefsapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


companion object {

    const val TIMES_PRESSED_KEY = "Times Pressed"
}



    var timespressed:Int = 0
    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs=getSharedPreferences("Sharedprefs", Context.MODE_PRIVATE)
        timespressed = prefs?.getInt(TIMES_PRESSED_KEY, 0) ?: 0

       value.text = timespressed.toString()

        btn_plusone.setOnClickListener{
            timespressed++
            value.text="$timespressed"
            prefs?.let {
                val editor = it.edit()
                editor.putInt(TIMES_PRESSED_KEY, timespressed)
                    .apply()
            }
            }




    }
}
