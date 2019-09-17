package com.saucefan.stuff.readinglist.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.saucefan.stuff.readinglist.App.Companion.localFiles
import com.saucefan.stuff.readinglist.R
import com.saucefan.stuff.readinglist.model.Book
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.getNewID
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.randBook
import com.saucefan.stuff.readinglist.viewmodel.LocalFiles
import com.saucefan.stuff.readinglist.viewmodel.SharedPrefsDao
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bookview.view.*

import timber.log.Timber.i


class MainActivity : AppCompatActivity(), EditFragment.OnFragmentInteractionListener {

    fun refreshCrappyRecycleView () {
        ll.removeAllViews()
        entryList.forEach { entry ->
            ll.addView(buildIemView(entry))
        }
    }

    private var entryList = mutableListOf<Book>()
    override fun onFragSave(book: Book) {
        SharedPrefsDao(this).createEntry(book)
        var found=false
        for (view in ll.children) {
            if (view.tag == book.id) {
                found=true
                view.title.text = book.title
                view.reasonToRead.text = book.reasonToRead
                view.tv_id_list.text = book.id.toString()
                if (book.hasBeenRead == true) {
                    view.background = getDrawable(R.color.bgHightlight)
                    view.checkBox.setChecked(true)
                    view.checkBox.invalidate()
                } else view.background = getDrawable(R.color.bgRegular)
                view.setOnClickListener {
                    //intent stuff
                    openFragForBook(book, view) }
            }
        }
        if (!found) {
            ll.addView(buildIemView(book))
            entryList.add(book)
            val manager = supportFragmentManager
            var list:DialogFragment = manager.findFragmentByTag("Edit Fragment") as DialogFragment
            list.dismiss()
        }else {
            //wonder if this will work
            val manager = supportFragmentManager
            var list:DialogFragment = manager.findFragmentByTag("Edit Fragment") as DialogFragment
            list.dismiss()
        }
        refreshCrappyRecycleView ()
    }


    override fun onStart() {
        super.onStart()
        i("onStart")
    }

    override fun onResume() {
        super.onResume()

        i("onResume")

        ll.removeAllViews()
        entryList.forEach { entry ->
            ll.addView(buildIemView(entry))
        }
    }

    override fun onPause() {
        super.onPause()

       i("onPause")
    }

    override fun onStop() {
        super.onStop()

       i("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        i("onDestroy")
    }
    fun buildIemView(book: Book): View {
        val item = layoutInflater.inflate(R.layout.bookview, null, false)
        item.tv_id_list.text = book.id.toString()
        item.tag = book.id
        item.title.text = "${book.title}"
        item.reasonToRead.text = "${book.reasonToRead}"
        if (book.hasBeenRead == true) {
            item.background = getDrawable(R.color.bgHightlight)
            item.checkBox.setChecked(true)
            item.checkBox.invalidate()
        } else {
            item.background = getDrawable(R.color.bgRegular)
            item.checkBox.setChecked(false)
            item.checkBox.invalidate()
        }
        item.setOnClickListener {
            //intent stuff
            openFragForBook(book, item)
        }
        item.checkBox.setOnClickListener {
            if (book.hasBeenRead == true) {
                item.background = getDrawable(R.color.bgRegular)
             //   item.checkBox.setChecked(true)
            //    item.checkBox.invalidate()
                book.hasBeenRead=false
            } else {

                item.background = getDrawable(R.color.bgHightlight)
              //  item.checkBox.setChecked(false)
             //   item.checkBox.invalidate()
                book.hasBeenRead=true
            }
        }
        return item
    }


    private fun openFragForBook(book: Book,
        item: View
    ) {
        val frag = EditFragment.newInstance(book.title.toString(), book)
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable(EDIT_BOOK, book)
        frag.arguments = bundle
        transaction.add(frag, "Edit Fragment")
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_whatever.setOnClickListener {
            val book=randBook()
            ll.addView(buildIemView(book))
            entryList.add(book)
        }
        btn_newitem.setOnClickListener {
            //making a new item is the same thing as editing an old item with default prompts, hence, to make a new item we're just going
            // to open up the same ol' edit fragment with a new book object --
            //9/17/2029 that turns out not to be entirely true, i think we want to get a new id for this now
            openFragForBook(Book("Enter title","Enter Reason to Read",false,getNewID()),btn_newitem)
        }

        localFiles= LocalFiles(this)
        entryList = localFiles!!.readAllEntries()
        entryList.forEach { entry ->
            ll.addView(buildIemView(entry))
        }

                //prefs.readAllEntries() ?:  mutableListOf<Book>(); i("we yelling timber")

    }
}
