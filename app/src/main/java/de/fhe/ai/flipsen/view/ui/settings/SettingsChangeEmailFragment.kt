package de.fhe.ai.flipsen.view.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import de.fhe.ai.flipsen.R
import de.fhe.ai.flipsen.database.local.PasswordDatabase
import de.fhe.ai.flipsen.database.local.dao.AccountDao

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsChangeEmailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class SettingsChangeEmailFragment : Fragment(R.layout.fragment_settings_change_email) {

    private lateinit var accountDao: AccountDao
    private lateinit var sharedPrefs: de.fhe.ai.flipsen.database.local.shared_prefs.ValueStore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(requireContext(), PasswordDatabase::class.java, "flipsen_db")
            .build()
        accountDao = db.passwordDao()
        sharedPrefs = de.fhe.ai.flipsen.database.local.shared_prefs.ValueStore(requireContext())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextNewEmail = view.findViewById<EditText>(R.id.settingsNewEmail)
        val editTextConfirmNewEmail = view.findViewById<EditText>(R.id.settingsNewEmailConfirmation)
        val button = view.findViewById<Button>(R.id.btnSettingsNewEmailConfirmation)
        val handler = Handler(Looper.getMainLooper())
        val navController = findNavController()

        val currentAccount = accountDao.getAccountById(sharedPrefs.getValue("accountId"))

        button.setOnClickListener {
            val newEmail: String = editTextNewEmail.text.toString()
            val newEmailConfirmation: String = editTextConfirmNewEmail.text.toString()

            //update database and SharedPreferences
            //give out message : account name(email) changed successfully and go back to settings
            if (newEmail == newEmailConfirmation){
                accountDao.updateAccountName(newEmailConfirmation, currentAccount.id)
                //sharedPreferences.saveAccountName(newEmailConfirmation)
                Toast.makeText(requireContext(), "Email-Adresse wurde erfolgreich geändert! Sie werden zu den Einstellungen zurückgebracht", Toast.LENGTH_SHORT).show()
                //redirects user to settings after 2000 milliseconds, so 2 seconds
                handler.postDelayed({
                    navController.navigate(R.id.navigation_settings)
                }, 2000)


                //error message
            } else {
                Toast.makeText(requireContext(), "Email-Adressen stimmen nicht überein!", Toast.LENGTH_SHORT).show()            }
        }
    }
}
