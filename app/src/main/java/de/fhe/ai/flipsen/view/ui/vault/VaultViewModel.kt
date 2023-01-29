package de.fhe.ai.flipsen.view.ui.vault

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import de.fhe.ai.flipsen.database.PasswordRepository

class VaultViewModel @ViewModelInject constructor(
    private val passwordRepository: PasswordRepository
): ViewModel() {
    val passwordEntryList = passwordRepository.getPasswords(0)

    private val _text = MutableLiveData<String>().apply {
        value = "Tresor"
    }

    val text: LiveData<String> = _text
}