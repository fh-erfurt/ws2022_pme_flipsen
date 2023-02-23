package de.fhe.ai.flipsen.view.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.content.Context
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import de.fhe.ai.flipsen.R
import de.fhe.ai.flipsen.databinding.FragmentSettingsBinding
import de.fhe.ai.flipsen.view.util.Utils
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var sharedPreferences: de.fhe.ai.flipsen.database.local.SharedPreferences
    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root //view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = de.fhe.ai.flipsen.database.local.SharedPreferences(requireContext())
        val navController = findNavController()

        //Profile name in settings
        val profileName = sharedPreferences.getAccountName()
        val textView = view.findViewById<TextView>(R.id.profile_name_shown)
        textView.text = profileName

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
        val sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        //Logout button
        btnLogout.setOnClickListener {
            fun logout(sharedPreferences: android.content.SharedPreferences, context: Context) {
                with(sharedPreferences.edit()) {
                    remove("account_name")
                    remove("master_password")
                    apply()
                }
            }
        }
    }
}
