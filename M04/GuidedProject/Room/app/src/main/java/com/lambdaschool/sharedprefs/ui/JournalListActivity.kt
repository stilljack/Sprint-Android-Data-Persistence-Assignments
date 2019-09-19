package com.lambdaschool.sharedprefs.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lambdaschool.sharedprefs.R
import com.lambdaschool.sharedprefs.entity.Journal.Companion.createJournalEntry
import com.lambdaschool.sharedprefs.model.JournalEntry
import com.lambdaschool.sharedprefs.repo
import kotlinx.android.synthetic.main.activity_journal_list.*
import kotlinx.android.synthetic.main.content_journal_list.*
import timber.log.Timber.i

class JournalListActivity : AppCompatActivity() {

    companion object {
        const val NEW_ENTRY_REQUEST = 2
        const val EDIT_ENTRY_REQUEST = 1
    }

    // TODO 27a: We don't need entryList anymore, but we do need a viewModel
    var entryList: MutableList<JournalEntry> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_list)
        setSupportActionBar(toolbar)

        // TODO 27: Get a ViewModel

        fab.setOnClickListener { view ->
            val intent = Intent(this@JournalListActivity, DetailsActivity::class.java)
            val entry = createJournalEntry()
            intent.putExtra(JournalEntry.TAG, entry)
            startActivityForResult(
                intent,
                NEW_ENTRY_REQUEST
            )
        }

        i("onCreate")

        // TODO 17: Replace the call here by an AsyncTask
        entryList = repo.readAllEntries()

        // TODO 22: Extract update functionality
        listLayout.removeAllViews()
        entryList.forEach { entry ->
            listLayout.addView(createEntryView(entry))
        }

        // TODO 28: Replace the call above by observing LiveData from the ViewModel
    }

    override fun onStart() {
        super.onStart()
        i("onStart")
    }

    override fun onResume() {
        super.onResume()

        i("onResume")

        // TODO 29: Do we need this code anymore?
        listLayout.removeAllViews()
        entryList.forEach { entry ->
            listLayout.addView(createEntryView(entry))
        }
    }

    // TODO 22: Extract update functionality

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

    private fun createEntryView(entry: JournalEntry): TextView {
        val view = TextView(this@JournalListActivity)

        view.text = getString(R.string.entry_label, entry.id, entry.date, entry.dayRating)

        view.setPadding(15, 15, 15, 15)
        view.textSize = 22f

        view.setOnClickListener {
            val viewDetailIntent = Intent(this@JournalListActivity, DetailsActivity::class.java)
            viewDetailIntent.putExtra(JournalEntry.TAG, entry)
            startActivityForResult(
                viewDetailIntent,
                EDIT_ENTRY_REQUEST
            )
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == NEW_ENTRY_REQUEST) {
                if (data != null) {
                    val entry = data.getSerializableExtra(JournalEntry.TAG) as JournalEntry
                    repo.createEntry(entry) // TODO 16a: Notice the call here, replace with AsyncTask
                }
            } else if (requestCode == EDIT_ENTRY_REQUEST) {
                if (data != null) {
                    val entry = data.getSerializableExtra(JournalEntry.TAG) as JournalEntry
                    entryList[entry.id] = entry
                    repo.updateEntry(entry) // TODO 16b. Notice the call here, replace with AsyncTask
                }
            }
        }
    }

    // TODO 19: Create AsyncTask
    // TODO 20: Update AsyncTask
    // TODO 21: ReadAll AsyncTask
}
