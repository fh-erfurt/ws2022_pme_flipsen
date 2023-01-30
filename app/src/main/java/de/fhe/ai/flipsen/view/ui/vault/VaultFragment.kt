package de.fhe.ai.flipsen.view.ui.vault

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import de.fhe.ai.flipsen.R
import de.fhe.ai.flipsen.databinding.FragmentVaultBinding
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.model.PasswordFolder
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
            }
        }

        vaultViewModel.passwordEntryList.observe(viewLifecycleOwner) {
            passwordEntryAdapter.submitList(
                it.map { item -> listOf(PasswordEntry(folder = item)) + item.passwordEntries }
                    .flatten()
            )
        }

        passwordEntryAdapter.onItemClick = { entry ->
            navigateToEditFragment(entry)
        }

        passwordEntryAdapter.onMenuClick = { button, entry ->
            val popupMenu = PopupMenu(activity, button)
            popupMenu.menuInflater.inflate(R.menu.entry_popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_delete -> vaultViewModel.deletePasswordEntry(entry)
                }
                true
            }
            popupMenu.show()
        }

        btnEditEntryFragment.setOnClickListener {
            navigateToEditFragment()
        }
    }

    private fun navigateToEditFragment(entry: PasswordEntry? = null) {
        val navController = findNavController()

        if (entry != null) {
            val bundle = Bundle()
            bundle.putParcelable("folder", entry.folder.copy(passwordEntries = listOf()))
            bundle.putParcelable("entry", entry.copy(folder = PasswordFolder()))
            navController.navigate(R.id.navigation_edit_entry, bundle)
            return
        }

        navController.navigate(R.id.navigation_edit_entry)
    }
}