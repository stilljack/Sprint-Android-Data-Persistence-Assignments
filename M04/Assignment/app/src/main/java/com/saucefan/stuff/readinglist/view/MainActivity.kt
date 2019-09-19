package com.saucefan.stuff.readinglist.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.saucefan.stuff.readinglist.App.Companion.localFiles
import com.saucefan.stuff.readinglist.R
import com.saucefan.stuff.readinglist.model.Book
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.deleteEntryFromRepo
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.entryList
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.getNewID
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.randBook
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.titleChangedBool
import com.saucefan.stuff.readinglist.viewmodel.LocalFiles
import com.saucefan.stuff.readinglist.viewmodel.SharedPrefsDao
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bookview.view.*
import timber.log.Timber.e

import timber.log.Timber.i


/*
*this app is bad and i can do better next time
*       if you have objects, you should be deleting THE OBJECT not an instance of the object living in
*           main activity. This app was hobbled by the instructions, which  led to stuff like refreshcrappyrecycleview()
*               and using ll.childcount to know how many entries have been made.
*
* so in the future i hope to remember, it may take longer to think through what the object IS -- where to store it,
* how it could change, how to deal with those changes and what the user should and shouldn't see when
* OBSERVING
* the object
*  otherwise you end up with a house of cards waiting to be breathed on too hard
*
* so:
* recycleview and possibly inherting from ViewModel
* maybe custom classes (view) that don't do a whole lot but express common behavior,
* like the checkboxes all turn their background colors based on book.hasbeenread
* with data binding, observers and live data we could be changing that instead of wrting a dumb
* if statement everythime i place a chekcbox
*
* cynical take:
* a poorly used week
* bright side:
* one step closer to being able to make apps that are readable, maintainable and parsimonious
*
*android jetpack... android jetpack... must understand android jetpack.
*
*/



class MainActivity : AppCompatActivity(), EditFragment.OnFragmentInteractionListener {


    override fun onDelete(book: Book) {
        //delete the file from the app's repo
        //and delete it from localfiles
        //obviously observing the book is a better model than this
        deleteEntryFromRepo(book)
        localFiles?.deleteEntry(book) ?: e("error on deleteEntry($book)")
        refreshCrappyRecycleView ()
    }

    fun refreshCrappyRecycleView () {
        ll.removeAllViews()
        entryList.forEach { entry ->
            ll.addView(buildIemView(entry))
        }
    }

    override fun onFragSave(book: Book) {
        //SharedPrefsDao(this).createEntry(book)
        var found=false

        for (view in ll.children) {
            if (view.tag == book.id) {
                found=true
                //before we do anything else, we need to check if this is a file that has had it's title changed
                if (titleChangedBool){
                    //since this is true, call delete on the old book we piece together from the contents of the view
                         localFiles?.deleteEntry(Book(view.title.text.toString(),
                            view.reasonToRead.text.toString(),
                            false,
                            0))
                    titleChangedBool=false

                    //now that i've made it this way, i think it might be smarter just to keep a reference to the book
                    //currently being edited and then call update on that -- shucks
                    //TODO: MAKE UPDATE ACTUALLY DO SOMETHING

                }

            }
        }
        if (!found) {
            ll.addView(buildIemView(book))
         //   val pref = SharedPrefsDao(this)
            //pref.createEntry(book)

          entryList.add(book)
        }
        localFiles?.createEntry(book) ?: i("shoot localfiles is borked on save")
        val manager = supportFragmentManager
        val list:DialogFragment = manager.findFragmentByTag("Edit Fragment") as DialogFragment
        list.dismiss()
        refreshCrappyRecycleView()
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
           randbox()
        }
        btn_newitem.setOnClickListener {
            //making a new item is the same thing as editing an old item with default prompts, hence, to make a new item we're just going
            // to open up the same ol' edit fragment with a new book object --
            //9/17/2029 that turns out not to be entirely true, i think we want to get a new id for this now
            openFragForBook(Book("Enter title","Enter Reason to Read",false,getNewID()),btn_newitem)
        }
 //      val pref =SharedPrefsDao(this)
  //    entryList = pref.readAllEntries()
    //   entryList.forEach { entry ->
 //        ll.addView(buildIemView(entry))
  //      }
    localFiles= LocalFiles(this)
        entryList = localFiles?.readAllEntries() as MutableList<Book>
        entryList.forEach { entry ->
            ll.addView(buildIemView(entry))}

                //prefs.readAllEntries() ?:  mutableListOf<Book>(); i("we yelling timber")

    }

    private fun randbox() {
        val book = randBook()
        ll.addView(buildIemView(book))
        entryList.add(book)
    }
}
