package de.fhe.ai.flipsen.view.ui.vault

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import de.fhe.ai.flipsen.R
import de.fhe.ai.flipsen.databinding.FragmentVaultBinding
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


        val navController = findNavController()

        btnEditEntryFragment.setOnClickListener {
            navController.navigate(R.id.navigation_edit_entry)
        }

        //TODO("Bug: List is not visible on first start, reloading the fragment resolves the issue")
    }

}