package de.fhe.ai.flipsen.view.ui.vault

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import de.fhe.ai.flipsen.databinding.FragmentVaultBinding

class VaultFragment : Fragment() {

    private lateinit var vaultViewModel: VaultViewModel
    private var _binding: FragmentVaultBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vaultViewModel = ViewModelProvider(this)[VaultViewModel::class.java]

        _binding = FragmentVaultBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textVault
        vaultViewModel.text.observe(viewLifecycleOwner, { textView.text = it })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}