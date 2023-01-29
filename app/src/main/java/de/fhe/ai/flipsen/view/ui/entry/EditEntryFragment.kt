package de.fhe.ai.flipsen.view.ui.entry

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.fhe.ai.flipsen.R
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import de.fhe.ai.flipsen.databinding.FragmentEditEntryBinding
import de.fhe.ai.flipsen.view.util.exhaustive

@AndroidEntryPoint
class EditEntryFragment : Fragment(R.layout.fragment_edit_entry) {

    private val viewModel : EditEntryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.edit_entry_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.btnSave -> {
                        viewModel.onSaveClick()
                        return true
                    }
                }
                return false
            }
        }, viewLifecycleOwner)

        val binding = FragmentEditEntryBinding.bind(view)

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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.editEntryEvent.collect { event ->
                when (event) {
                    is EditEntryViewModel.EditEntryEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG)
                                .show()
                    }
                    is EditEntryViewModel.EditEntryEvent.NavigateBackWithResult -> {
                        binding.inputName.clearFocus()
                        setFragmentResult(
                            "edit_request",
                            bundleOf("edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                    else -> {}
                }.exhaustive
            }
        }
    }
}