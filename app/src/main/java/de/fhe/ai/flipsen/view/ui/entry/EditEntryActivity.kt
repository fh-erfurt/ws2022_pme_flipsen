package de.fhe.ai.flipsen.view.ui.entry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import de.fhe.ai.flipsen.R
import de.fhe.ai.flipsen.database.local.PasswordDatabase
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.view.MainActivity
import kotlinx.android.synthetic.main.activity_edit_entry.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditEntryActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_entry)

        window.statusBarColor = ContextCompat.getColor(this, R.color.neutral_500)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Eintrag bearbeiten"

        imageView = findViewById(R.id.imageView)
        Picasso.get().load("https://unfair-white-tarantula.faviconkit.com/netflix.com/32").placeholder(R.drawable.delete_sweep)
            .error(R.drawable.delete_sweep)
            .into(imageView)

        //val btnSave = findViewById<Button>(R.id.btnSave)
        //btnSave.setOnClickListener {
        //    writeData()
        //}

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_entry_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //private fun writeData() {
    //    val name        = inputName.text.toString()
    //    val username    = inputUsername.text.toString()
    //    val password    = inputPassword.text.toString()
    //    val URL         = inputURL.text.toString()
    //    val folder      = inputFolder.text.toString()
    //    val notes       = inputNotes.text.toString()
    //
    //    if (name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
    //        val passwordEntry = PasswordEntry(
    //            name, username, password, URL
    //        )
    //
    //    }
    //
    //}

}