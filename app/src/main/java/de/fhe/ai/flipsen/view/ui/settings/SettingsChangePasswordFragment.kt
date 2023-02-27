package de.fhe.ai.flipsen.view.ui.settings

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
 * Use the [SettingsChangePasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class SettingsChangePasswordFragment : Fragment(R.layout.fragment_settings_change_password) {


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

        //if error with `requireActivity().` test like in SettingsFragment with override fun onViewCreated!!!
        val editTextNewMasterPassword = view.findViewById<EditText>(R.id.settingsNewPassword)
        val editTextConfirmNewMasterPassword = view.findViewById<EditText>(R.id.settingsNewPasswordConfirmation)
        val button = view.findViewById<Button>(R.id.btnSettingsNewPasswordConfirmation)
        val handler = Handler(Looper.getMainLooper())
        val navController = findNavController()

        val currentAccount = accountDao.getAccountById(sharedPrefs.getValue("accountId"))

        button.setOnClickListener {
            val newMasterPassword: String = editTextNewMasterPassword.text.toString()
            val newMasterPasswordConfirmation: String = editTextConfirmNewMasterPassword.text.toString()

            //update database and SharedPreferences
            //give out message : password changed successfully and go back to settings
            if (newMasterPassword == newMasterPasswordConfirmation){
                accountDao.updateMasterPassword(newMasterPasswordConfirmation, currentAccount.id)
               //sharedPreferences.saveMasterPassword(newMasterPasswordConfirmation)
                Toast.makeText(requireContext(), "Passwort wurde erfolgreich geändert! Sie werden zu den Einstellungen zurückgebracht", Toast.LENGTH_SHORT).show()
                //redirects user to settings after 2000 milliseconds, so 2 seconds
                handler.postDelayed({
                    navController.navigate(R.id.navigation_settings)
                }, 2000)


                //error message
            } else {
                Toast.makeText(requireContext(), "Passwörter stimmen nicht überein!", Toast.LENGTH_SHORT).show()            }
        }
    }
}