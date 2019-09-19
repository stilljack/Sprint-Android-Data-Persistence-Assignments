package com.lambdaschool.sharedprefs.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lambdaschool.sharedprefs.R
import com.lambdaschool.sharedprefs.entity.Journal.Companion.createJournalEntry
import com.lambdaschool.sharedprefs.model.JournalEntry
import com.lambdaschool.sharedprefs.prefs
import kotlinx.android.synthetic.main.activity_journal_list.*
import kotlinx.android.synthetic.main.content_journal_list.*
import timber.log.Timber.i

// TODO: 6. Create Home Screen - shows a list of Journal Entries
class JournalListActivity : AppCompatActivity() {

    companion object {
        const val NEW_ENTRY_REQUEST = 2
        const val EDIT_ENTRY_REQUEST = 1
    }

    private var entryList = mutableListOf<JournalEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_list)
        setSupportActionBar(toolbar)

        // TODO: 7. Clicking the FloatingActionButton goes to the DetailsActivity - to create a new Journal Entry
        fab.setOnClickListener { view ->
            val intent = Intent(this@JournalListActivity, DetailsActivity::class.java)
            val entry = createJournalEntry()
            intent.putExtra(JournalEntry.TAG, entry)
            startActivityForResult(
                intent,
                NEW_ENTRY_REQUEST // TODO: 8. This intent is for NEW ENTRIES (not editing an existing entry)
            )
        }

        i("onCreate")

        // Stretch goal: add test entries on first launch:
//        Journal.createTestEntries(prefs)

        entryList = prefs.readAllEntries()
    }

    override fun onStart() {
        super.onStart()
        i("onStart")
    }

    override fun onResume() {
        super.onResume()

        i("onResume")

        listLayout.removeAllViews()
        entryList.forEach { entry ->
            listLayout.addView(createEntryView(entry))
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

    private fun createEntryView(entry: JournalEntry): TextView {
        val view = TextView(this@JournalListActivity)

        // TODO: 9. This line defines how each list entry will appear (textually)
        view.text = getString(R.string.entry_label, entry.id, entry.date, entry.dayRating)

        view.setPadding(15, 15, 15, 15)
        view.textSize = 22f

        // TODO: 10. Clicking on an item in the list takes you to the DetailsActivity
        view.setOnClickListener {
            val viewDetailIntent = Intent(this@JournalListActivity, DetailsActivity::class.java)
            viewDetailIntent.putExtra(JournalEntry.TAG, entry)
            startActivityForResult(
                viewDetailIntent,
                EDIT_ENTRY_REQUEST // TODO 11. This intent is for EDITING an existing Journal Entry
            )
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // TODO: 12. This tells us whether we have been Editing or Creating a new Journal Entry
        if (resultCode == RESULT_OK) {
            if (requestCode == NEW_ENTRY_REQUEST) {
                if (data != null) {
                    val entry = data.getSerializableExtra(JournalEntry.TAG) as JournalEntry
                    entryList.add(entry)
                    prefs.createEntry(entry) // TODO 13. We create the new Journal Entry via Shared Preferences
                }
            } else if (requestCode == EDIT_ENTRY_REQUEST) {
                if (data != null) {
                    val entry = data.getSerializableExtra(JournalEntry.TAG) as JournalEntry
                    entryList[entry.id] = entry
                    prefs.updateEntry(entry) // TODO 14. We update the existing Journal Entry via Shared Preferences
                }
            }
        }
    }
}
