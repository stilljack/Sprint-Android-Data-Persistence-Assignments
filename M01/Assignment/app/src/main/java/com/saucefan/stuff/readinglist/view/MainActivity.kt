package com.saucefan.stuff.readinglist.view

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ContentView
import com.saucefan.stuff.readinglist.R
import com.saucefan.stuff.readinglist.model.Book
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.randBook
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bookview.*
import kotlinx.android.synthetic.main.bookview.view.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    var itemsInList = 0


    fun buildIemView(book: Book): View {
        /*    book.title
            book.id
            book.reasonToRead
            book.hasBeenRead*/

        var item = layoutInflater.inflate(R.layout.bookview, null, false)

        item.title.text="${book.title}"
        item.reasonToRead.text="${book.reasonToRead}"
        if (book.hasBeenRead == true) {
            item.background= getDrawable(R.color.bgHightlight)
            item.checkBox.setChecked(true)
            item.checkBox.invalidate()

        } else item.background = getDrawable(R.color.bgRegular)
        return item



    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_whatever.setOnClickListener {
            ll.addView(buildIemView(randBook()))
        }
    }
}
