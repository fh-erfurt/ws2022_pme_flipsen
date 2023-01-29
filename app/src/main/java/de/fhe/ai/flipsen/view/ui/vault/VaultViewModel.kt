package de.fhe.ai.flipsen.view.ui.vault

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fhe.ai.flipsen.database.PasswordRepository
import de.fhe.ai.flipsen.model.PasswordEntry
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VaultViewModel @Inject constructor(private val passwordRepository: PasswordRepository): ViewModel() {
    val passwordEntryList = passwordRepository.getPasswords(0)

    fun deletePasswordEntry(entry: PasswordEntry) {
        viewModelScope.launch {
            passwordRepository.delete(entry)
        }
    }
}