package com.saucefan.stuff.readinglist.view

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.saucefan.stuff.readinglist.R
import com.saucefan.stuff.readinglist.model.Book
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.entryList
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.getNewID
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.randBook
import com.saucefan.stuff.readinglist.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bookview.view.*
import timber.log.Timber.e
import timber.log.Timber.i
import java.lang.ref.WeakReference

/*
* 9/19/2016 --- room migration
*
* losing a ton of useless code out of here.
*
*
* */
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
    lateinit var viewModel: BookViewModel

    override fun onDelete(book: Book) {
        //deleteEntryFromRepo(book)
        DeleteAsyncTask(viewModel).execute(book) ?: e("error on deleteEntry($book)")
    }

    fun refreshCrappyRecycleView (entryList:List<Book>) {
        ll.removeAllViews()
        entryList.forEach { entry ->
            ll.addView(buildIemView(entry))
        }
    }

    override fun onFragSave(book: Book) {
        CreateAsyncTask(viewModel).execute(book) ?: i("shoot localfiles is borked on save")
        val manager = supportFragmentManager
        val list:DialogFragment = manager.findFragmentByTag("Edit Fragment") as DialogFragment
        list.dismiss()
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
           openFragForBook(book, item)
        }
        item.checkBox.setOnClickListener {
            if (book.hasBeenRead == true) {
                item.background = getDrawable(R.color.bgRegular)
                book.hasBeenRead=false
            } else {
                item.background = getDrawable(R.color.bgHightlight)
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
        viewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)
        ReadAllAsyncTask(this).execute()
        entryList.forEach { entry ->
            ll.addView(buildIemView(entry))}


    }

    class CreateAsyncTask(viewModel: BookViewModel) : AsyncTask<Book, Void, Unit>() {
        private val viewModel = WeakReference(viewModel)
        override fun doInBackground(vararg entries: Book?) {
            if (entries.isNotEmpty()) {
                entries[0]?.let {
                    viewModel.get()?.createEntry(it)
                }
            }
        }
    }

    // TODO 20: Create AsyncTasks
    class UpdateAsyncTask(viewModel: BookViewModel) : AsyncTask<Book, Void, Unit>() {
        private val viewModel = WeakReference(viewModel)
        override fun doInBackground(vararg entries: Book?) {
            if (entries.isNotEmpty()) {
                entries[0]?.let {
                    viewModel.get()?.updateEntry(it)
                }
            }
        }
    }

    class DeleteAsyncTask(viewModel: BookViewModel) : AsyncTask<Book, Void, Unit>() {
        private val viewModel = WeakReference(viewModel)
        override fun doInBackground(vararg entries: Book?) {
            if (entries.isNotEmpty()) {
                entries[0]?.let {
                    viewModel.get()?.deleteEntry(it)
                }
            }
        }
    }

    class ReadAllAsyncTask(activity: MainActivity) : AsyncTask<Void, Void, LiveData<List<Book>>?>() {

        private val activity = WeakReference(activity)

        override fun doInBackground(vararg entries: Void?): LiveData<List<Book>>? {
            return activity.get()?.viewModel?.entries
        }

        override fun onPostExecute(result: LiveData<List<Book>>?) {
            activity.get()?.let { act ->
                result?.let { entries ->
                    // TODO 27: Observe LiveData here
                    entries.observe(act,
                            Observer<List<Book>> { t ->
                                t?.let {
                                    act.refreshCrappyRecycleView(t)
                                }
                            }
                    )
                }
            }
        }
    }



    private fun randbox() {
        val book = randBook()
        ll.addView(buildIemView(book))
        entryList.add(book)
    }




    override fun onStart() {
        super.onStart()
        i("onStart")
    }

    override fun onResume() {
        super.onResume()
        i("onResume")

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
}
