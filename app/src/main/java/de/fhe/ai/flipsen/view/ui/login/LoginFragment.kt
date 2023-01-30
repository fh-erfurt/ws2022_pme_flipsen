package de.fhe.ai.flipsen.view.ui.login

import android.content.Intent
import de.fhe.ai.flipsen.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import de.fhe.ai.flipsen.databinding.FragmentLoginBinding
import de.fhe.ai.flipsen.view.MainActivity


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)

        binding.apply {
            tietAccountName.setText(viewModel.accountLoginAccountName)
            tietMasterPassword.setText(viewModel.accountLoginMasterPassword)

            tietAccountName.addTextChangedListener {
                viewModel.accountLoginAccountName = it.toString()
            }

            tietMasterPassword.addTextChangedListener {
                viewModel.accountLoginMasterPassword = it.toString()
            }
        }

        val navController = findNavController()
        val buttonToRegister = view.findViewById<Button>(R.id.switch_to_register_btn)
        val buttonLogin = view.findViewById<Button>(R.id.button_login)

        buttonToRegister.setOnClickListener {
            navController.navigate(R.id.navigation_register)
        }

        buttonLogin.setOnClickListener {
            val loginAccount = viewModel.onLoginClick()

            if (loginAccount == -1) {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loginEvent.collect { event ->
                when (event) {
                    is LoginViewModel.LoginEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }

    }
}