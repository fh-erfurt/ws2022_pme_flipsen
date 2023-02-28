package de.fhe.ai.flipsen.view.ui.settings

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import de.fhe.ai.flipsen.R
import de.fhe.ai.flipsen.database.local.PasswordDatabase
import de.fhe.ai.flipsen.database.local.dao.AccountDao
import de.fhe.ai.flipsen.databinding.FragmentSettingsBinding
import de.fhe.ai.flipsen.databinding.FragmentSettingsChangePasswordBinding
import de.fhe.ai.flipsen.view.ui.login.RegisterViewModel

@AndroidEntryPoint
class SettingsChangePasswordFragment : Fragment(R.layout.fragment_settings_change_password) {


    private val viewModel : SettingsPasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSettingsChangePasswordBinding.bind(view)
        binding.apply {
            settingsNewPassword.setText(viewModel.accountLoginAccountPassword)
            settingsNewPasswordConfirmation.setText(viewModel.accountLoginAccountPasswordConfirm)

            settingsNewPassword.addTextChangedListener {
                viewModel.accountLoginAccountPassword = it.toString()
            }
            settingsNewPasswordConfirmation.addTextChangedListener {
                viewModel.accountLoginAccountPasswordConfirm = it.toString()
            }
        }

        val button = view.findViewById<Button>(R.id.btnSettingsNewPasswordConfirmation)
        val handler = Handler(Looper.getMainLooper())
        val navController = findNavController()

        button.setOnClickListener {
            val newMasterPassword = viewModel.onChangeClickPassword()
            if (newMasterPassword == -1) {
                Toast.makeText(
                    requireContext(),
                    "Passwort wurde erfolgreich geändert!",
                    Toast.LENGTH_SHORT
                ).show()
                //redirects user to settings after 2000 milliseconds, so 2 seconds
                handler.postDelayed({
                    navController.navigate(R.id.navigation_settings)
                }, 2000)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.settingsEvent.collect { event ->
                when (event) {
                    is SettingsPasswordViewModel.SettingsEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG)
                            .show()
                    }
                    is SettingsPasswordViewModel.SettingsEvent.NavigateBackWithResult -> {
                        setFragmentResult(
                            "edit_request",
                            bundleOf("edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                    else -> {}
                }
            }
        }
    }
}

