package de.fhe.ai.flipsen.view.ui.entry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import de.fhe.ai.flipsen.R
import de.fhe.ai.flipsen.database.local.PasswordDatabase
import de.fhe.ai.flipsen.databinding.ActivityEditEntryBinding
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.view.MainActivity
import kotlinx.android.synthetic.main.activity_edit_entry.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditEntryActivity : AppCompatActivity() {

    private val viewModel : EditEntryViewModel by viewModels()
    private lateinit var binding: ActivityEditEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditEntryBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_edit_entry)

        window.statusBarColor = ContextCompat.getColor(this, R.color.neutral_500)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Eintrag bearbeiten"

        binding.apply {
            inputName.setText(viewModel.passwordEntryName)
            inputUsername.setText(viewModel.passwordEntryUsername)
            inputPassword.setText(viewModel.passwordEntryPassword)
            inputURL.setText(viewModel.passwordEntryURL)

            // Group & Account

            inputName.addTextChangedListener {
                viewModel.passwordEntryName = it.toString()
            }

            inputUsername.addTextChangedListener {
                viewModel.passwordEntryUsername = it.toString()
            }

            inputPassword.addTextChangedListener {
                viewModel.passwordEntryPassword = it.toString()
            }

            inputURL.addTextChangedListener {
                viewModel.passwordEntryURL = it.toString()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_entry_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}