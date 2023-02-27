package de.fhe.ai.flipsen.view.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.content.Context
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import de.fhe.ai.flipsen.R
import de.fhe.ai.flipsen.database.local.dao.AccountDao
import de.fhe.ai.flipsen.databinding.FragmentRegisterBinding
import de.fhe.ai.flipsen.databinding.FragmentSettingsBinding
import de.fhe.ai.flipsen.view.ui.login.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var settingsPasswordViewModel: SettingsPasswordViewModel
    private lateinit var settingsEmailViewModel: SettingsEmailViewModel
    private lateinit var sharedPrefs: de.fhe.ai.flipsen.database.local.shared_prefs.ValueStore
    private lateinit var accountDao: AccountDao
    private var _binding: FragmentSettingsBinding? = null
    private val viewModelPassword : SettingsPasswordViewModel by viewModels()
    private val viewModelEmail : SettingsEmailViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSettingsBinding.bind(view)

        binding.apply {}


        val navController = findNavController()
        val btnChangeEmail = view.findViewById<Button>(R.id.btnChangeEmail)
        val btnChangePassword =view.findViewById<Button>(R.id.btnChangePassword)

        //Navigations for the buttons
        btnChangeEmail.setOnClickListener {
            navController.navigate(R.id.navigation_settings_change_email)
        }
        btnChangePassword.setOnClickListener {
            navController.navigate(R.id.navigation_settings_change_password)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /*  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefs = de.fhe.ai.flipsen.database.local.shared_prefs.ValueStore(requireContext())
        val navController = findNavController()

        //Profile name in settings
        val profileName = accountDao.getAccountById(sharedPrefs.getValue("accountId"))
        val textView = view.findViewById<TextView>(R.id.profile_name_shown)
        textView.text = profileName.toString()

      /*val btnChangeEmail = view.findViewById<Button>(R.id.btnChangeEmail)
        val btnChangePassword =view.findViewById<Button>(R.id.btnChangePassword)*/

       //Navigations for the buttons
       btnChangeEmail.setOnClickListener {
           navController.navigate(R.id.navigation_settings_change_email)
       }
       btnChangePassword.setOnClickListener {
           navController.navigate(R.id.navigation_settings_change_password)
       }



       //requireActivity().findViewById allows you to access views that are part of the parent activity's layout
       //view.findViewById allows you to access views that are part of the fragment's layout
       val btnLogout = view.findViewById<Button>(R.id.btnLogout)
       val sharedPrefs = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

       //Logout button
       btnLogout.setOnClickListener {
           fun logout(sharedPrefs: android.content.SharedPreferences, context: Context) {
               with(sharedPrefs.edit()) {
                   remove("account_name")
                   remove("master_password")
                   apply()
               }
           }
       }
   }*/
}
