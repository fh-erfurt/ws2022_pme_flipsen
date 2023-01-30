package de.fhe.ai.flipsen.view.ui.vault

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fhe.ai.flipsen.database.PasswordFolderRepository
import de.fhe.ai.flipsen.database.PasswordRepository
import de.fhe.ai.flipsen.model.PasswordEntry
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VaultViewModel @Inject constructor(
    passwordFolderRepository: PasswordFolderRepository,
    private val passwordRepository: PasswordRepository
) :
    ViewModel() {
    val passwordEntryList = passwordFolderRepository.getFolderForUser(1)

    fun deletePasswordEntry(entry: PasswordEntry) {
        viewModelScope.launch {
            passwordRepository.delete(entry)
        }
    }
}