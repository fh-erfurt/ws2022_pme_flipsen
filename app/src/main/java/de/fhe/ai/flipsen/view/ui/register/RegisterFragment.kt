package de.fhe.ai.flipsen.view.ui.login

import android.content.Intent
import de.fhe.ai.flipsen.R
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.util.newStringBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import de.fhe.ai.flipsen.databinding.FragmentRegisterBinding
import de.fhe.ai.flipsen.view.MainActivity

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel : RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRegisterBinding.bind(view)

        binding.apply {
            tietRegisterAccountName.setText(viewModel.accountRegisterAccountName)
            tietRegisterMasterPassword.setText(viewModel.accountRegisterMasterPassword)

            tietRegisterAccountName.addTextChangedListener {
                viewModel.accountRegisterAccountName = it.toString()
            }

            tietRegisterMasterPassword.addTextChangedListener {
                viewModel.accountRegisterMasterPassword = it.toString()
            }
        }

        val navController = findNavController()
        val buttonToLogin = view.findViewById<Button>(R.id.switch_to_login_btn)
        val buttonRegister = view.findViewById<Button>(R.id.button_register)

        buttonToLogin.setOnClickListener {
            navController.navigate(R.id.navigation_login)
        }

        buttonRegister.setOnClickListener{
            val newAccount = viewModel.onRegisterClick()

            if (newAccount == -1) {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.registerEvent.collect { event ->
                when (event) {
                    is RegisterViewModel.RegisterEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG)
                            .show()
                    }
                    is RegisterViewModel.RegisterEvent.NavigateBackWithResult -> {
                        //binding.inputName.clearFocus()
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
