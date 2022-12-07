package de.fhe.ai.flipsen.view.ui.vault

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VaultViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Tresor"
    }

    val text: LiveData<String> = _text
}