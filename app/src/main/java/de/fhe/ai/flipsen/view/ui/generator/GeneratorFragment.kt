package de.fhe.ai.flipsen.view.ui.generator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import de.fhe.ai.flipsen.databinding.FragmentGeneratorBinding

class GeneratorFragment : Fragment() {

    private lateinit var generatorViewModel: GeneratorViewModel
    private var _binding: FragmentGeneratorBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        generatorViewModel = ViewModelProvider(this)[GeneratorViewModel::class.java]

        _binding = FragmentGeneratorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGenerator
        generatorViewModel.text.observe(viewLifecycleOwner) { textView.text = it }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}