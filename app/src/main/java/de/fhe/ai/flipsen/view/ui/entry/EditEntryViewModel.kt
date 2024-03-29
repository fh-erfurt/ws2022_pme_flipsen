package de.fhe.ai.flipsen.view.ui.entry

import android.app.Activity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fhe.ai.flipsen.database.PasswordFolderRepository
import de.fhe.ai.flipsen.database.PasswordRepository
import de.fhe.ai.flipsen.database.local.shared_prefs.ValueStore
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.model.PasswordFolder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEntryViewModel @Inject constructor(
    private val passwordRepository: PasswordRepository,
    private val folderRepository: PasswordFolderRepository,
    private val sharedPrefs: ValueStore,
    private val state : SavedStateHandle
) : ViewModel() {

    var passwordFolder = state.get<PasswordFolder>("passwordFolder")
        set(value) {
            field = value
            state["passwordFolder"] = value
        }

    var passwordFolderName = state.get<String>("passwordFolderName") ?: passwordFolder?.name ?: "Unsortiert"
        set(value) {
            field = value
            state["passwordFolderName"] = value
        }

    var passwordEntry = state.get<PasswordEntry>("passwordEntry")
        set(value) {
            field = value
            state["passwordEntry"] = value
        }

    var passwordEntryName = state.get<String>("passwordEntryName") ?: passwordEntry?.name ?: ""
        set(value) {
            field = value
            state["passwordEntryName"] = value
        }

    var passwordEntryUsername = state.get<String>("passwordEntryUsername") ?: passwordEntry?.username ?: ""
        set(value) {
            field = value
            state["passwordEntryUsername"] = value
        }

    var passwordEntryPassword = state.get<String>("passwordEntryPassword") ?: passwordEntry?.password ?: ""
        set(value) {
            field = value
            state["passwordEntryPassword"] = value
        }

    var passwordEntryURL = state.get<String>("passwordEntryURL") ?: passwordEntry?.URL ?: ""
        set(value) {
        field = value
            state["passwordEntryURL"] = value
        }

    private val editEntryEventChannel = Channel<EditEntryEvent>()
    val editEntryEvent = editEntryEventChannel.receiveAsFlow()

    fun onSaveClick() {
        if (passwordEntryName.isBlank()) {
            showInvalidInputMessage("Name can not be empty")
            return
        }

        if (passwordEntryPassword.isBlank()) {
            showInvalidInputMessage("Password can not be empty")
            return
        }

        if (passwordEntry != null) {
            val updatedPasswordEntry = passwordEntry!!.copy(
                name = passwordEntryName,
                username = passwordEntryUsername,
                password = passwordEntryPassword,
                URL = passwordEntryURL
            )
            updatePasswordEntry(updatedPasswordEntry)
        } else {
            val newPasswordEntry = PasswordEntry(
                name = passwordEntryName,
                username = passwordEntryUsername,
                password = passwordEntryPassword,
                URL = passwordEntryURL
            )
            createPasswordEntry(newPasswordEntry)
        }
    }

    val accountId = sharedPrefs.getValue("accountId")
    private fun createPasswordEntry(passwordEntry: PasswordEntry) = viewModelScope.launch {
        val folder = folderRepository.getFolderByName(accountId, passwordFolderName)

        if (folder == null) {
            folderRepository.insert(PasswordFolder(accountId = accountId.toInt(), name = passwordFolderName))
        }

        val newFolder = folderRepository.getFolderByName(accountId, passwordFolderName)!!
        passwordRepository.insert(passwordEntry.copy(folderId = newFolder.id))

        editEntryEventChannel.send(EditEntryEvent.NavigateBackWithResult(ADD_ENTRY_RESULT_OK))
    }

    private fun updatePasswordEntry(passwordEntry: PasswordEntry) = viewModelScope.launch {
        val folder = folderRepository.getFolderByName(accountId, passwordFolderName)
        if (folder == null) folderRepository.insert(PasswordFolder(sharedPrefs.getValue("accountId"), name = passwordFolderName))
        val newFolder = folderRepository.getFolderByName(accountId, passwordFolderName)!!

        passwordRepository.update(passwordEntry.copy(folderId = newFolder.id))
        editEntryEventChannel.send(EditEntryEvent.NavigateBackWithResult(EDIT_ENTRY_RESULT_OK))
    }

    private fun showInvalidInputMessage(text : String) = viewModelScope.launch {
        editEntryEventChannel.send(EditEntryEvent.ShowInvalidInputMessage(text))
    }

    sealed class EditEntryEvent {
        data class ShowInvalidInputMessage(val msg: String) : EditEntryEvent()
        data class NavigateBackWithResult(val result: Int) : EditEntryEvent()
    }
}

const val ADD_ENTRY_RESULT_OK = Activity.RESULT_FIRST_USER
const val  EDIT_ENTRY_RESULT_OK = Activity.RESULT_FIRST_USER + 1