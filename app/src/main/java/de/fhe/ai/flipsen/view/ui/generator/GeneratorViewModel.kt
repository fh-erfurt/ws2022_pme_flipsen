package de.fhe.ai.flipsen.view.ui.generator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GeneratorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Generator"
    }
    val text: LiveData<String> = _text
}