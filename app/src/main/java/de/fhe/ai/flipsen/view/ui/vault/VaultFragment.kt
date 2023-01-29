package de.fhe.ai.flipsen.view.ui.vault

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import de.fhe.ai.flipsen.R
import de.fhe.ai.flipsen.databinding.FragmentVaultBinding
import de.fhe.ai.flipsen.model.PasswordEntry
import kotlinx.android.synthetic.main.fragment_vault.*

@AndroidEntryPoint
class VaultFragment : Fragment(R.layout.fragment_vault) {

    private val vaultViewModel: VaultViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentVaultBinding.bind(view)

        val passwordEntryAdapter = PasswordEntryAdapter()

        binding.apply {
            rvEntries.apply {
                adapter = passwordEntryAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        vaultViewModel.passwordEntryList.observe(viewLifecycleOwner) {
            passwordEntryAdapter.submitList(it)
        }

        passwordEntryAdapter.onItemClick = { entry ->
            navigateToEditFragment(entry)
        }

        btnEditEntryFragment.setOnClickListener {
            navigateToEditFragment()
        }
    }

    private fun navigateToEditFragment(entry: PasswordEntry? = null) {
        val navController = findNavController()

        if (entry != null) {
            val bundle = Bundle()
            bundle.putParcelable("entry", entry)
            navController.navigate(R.id.navigation_edit_entry, bundle)
            return;
        }

        navController.navigate(R.id.navigation_edit_entry)
    }

}