package com.lambdaschool.sharedprefs.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.lambdaschool.sharedprefs.R
import com.lambdaschool.sharedprefs.model.JournalEntry
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber.i

// TODO: 25. Activity to show Details of a Journal Entry - whether for a brand new entry, for to edit an existing entry
class DetailsActivity : AppCompatActivity() {

    companion object {
        private const val IMAGE_REQUEST_CODE = 50
    }

    private var entry: JournalEntry =
        JournalEntry(JournalEntry.INVALID_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        i("onCreate")

        val intent = intent
        // TODO: 26. Get the JournalEntry as passed in to this activity
        entry = intent.getSerializableExtra(JournalEntry.TAG) as JournalEntry

        entry_id_label.text = "#${entry.id}"

        journal_entry_date.text = entry.date

        journal_entry_text.setText(entry.entryText)
        journal_entry_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                val entryString = s.toString()
                entry.entryText = entryString
            }
        })

        journal_entry_seekbar.max = 5
        journal_entry_seekbar.progress = entry.dayRating
        journal_entry_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    entry.dayRating = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        val imageUri = entry.getImage()
        if (imageUri != null) {
            journal_entry_image?.setImageURI(imageUri)
        }

        add_image_button.setOnClickListener {
            // TODO: 27: Ask for an Image from the device (Intent)
            val imageIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            imageIntent.apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
            startActivityForResult(
                imageIntent,
                IMAGE_REQUEST_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQUEST_CODE && data != null) {
            data.data?.let {
                // TODO: 28: If an image was selected, set it in the Journal Entry
                entry.setImage(it)
                journal_entry_image.setImageURI(it)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        i("onStart")
    }

    override fun onResume() {
        super.onResume()
        i("onResume")
    }

    // user interacting with app

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            // TODO: 29. "SAVE" Menu Item to save/update a Journal Entry
            R.id.action_save -> {
                val resultIntent = Intent()
                resultIntent.putExtra(JournalEntry.TAG, entry)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
