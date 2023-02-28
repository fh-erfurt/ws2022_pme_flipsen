package de.fhe.ai.flipsen.view.ui.settings

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
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
import de.fhe.ai.flipsen.databinding.FragmentSettingsChangeEmailBinding

@AndroidEntryPoint
class SettingsChangeEmailFragment : Fragment(R.layout.fragment_settings_change_email) {

    private val viewModel : SettingsEmailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSettingsChangeEmailBinding.bind(view)
        binding.apply {
            settingsNewEmail.setText(viewModel.accountLoginAccountName)
            settingsNewEmailConfirmation.setText(viewModel.accountLoginAccountNameConfirm)

            settingsNewEmail.addTextChangedListener {
                viewModel.accountLoginAccountName = it.toString()
            }
            settingsNewEmailConfirmation.addTextChangedListener {
                viewModel.accountLoginAccountNameConfirm = it.toString()
            }
        }

        val button = view.findViewById<Button>(R.id.btnSettingsNewEmailConfirmation)
        val handler = Handler(Looper.getMainLooper())
        val navController = findNavController()

        button.setOnClickListener {
            val newEmail = viewModel.onChangeClickEmail()
            if (newEmail == -2) {
                Toast.makeText(
                    requireContext(),
                    "Email wurde erfolgreich geändert!",
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
                    is SettingsEmailViewModel.SettingsEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG)
                            .show()
                    }
                    is SettingsEmailViewModel.SettingsEvent.NavigateBackWithResult -> {
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
