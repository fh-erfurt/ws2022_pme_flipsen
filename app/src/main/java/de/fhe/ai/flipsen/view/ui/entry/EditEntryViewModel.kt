package de.fhe.ai.flipsen.view.ui.entry

import android.app.Activity
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.ai.flipsen.database.local.PasswordDao
import de.fhe.ai.flipsen.model.PasswordEntry
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditEntryViewModel @ViewModelInject constructor(
    private val passwordDao: PasswordDao,
    @Assisted private val state : SavedStateHandle
) : ViewModel() {

    var passwordEntry = state.get<PasswordEntry>("passwordEntry")
        set(value) {
            field = value
            state.set("passwordEntry", value)
            state.set("passwordEntryName", value?.name)
            state.set("passwordEntryUsername", value?.username)
            state.set("passwordEntryPassword", value?.password)
            state.set("passwordEntryURL", value?.URL)
        }

    var passwordEntryName = state.get<String>("passwordEntryName") ?: passwordEntry?.name ?: ""
        set(value) {
            field = value
            state.set("passwordEntryName", value)
        }

    var passwordEntryUsername = state.get<String>("passwordEntryUsername") ?: passwordEntry?.username ?: ""
        set(value) {
            field = value
            state.set("passwordEntryUsername", value)
        }

    var passwordEntryPassword = state.get<String>("passwordEntryPassword") ?: passwordEntry?.password ?: ""
        set(value) {
            field = value
            state.set("passwordEntryPassword", value)
        }

    var passwordEntryURL = state.get<String>("passwordEntryURL") ?: passwordEntry?.URL ?: ""
        set(value) {
        field = value
        state.set("passwordEntryURL", value)
        }

    // Group_id, Account_id?

    private val editEntryEventChannel = Channel<EditEntryEvent>()
    val editEntryEvent = editEntryEventChannel.receiveAsFlow()

    fun setState(entry: PasswordEntry) {
        passwordEntryName = entry.name
        passwordEntryUsername = entry.username
        passwordEntryPassword = entry.password
        passwordEntryURL = entry.URL
    }

    fun onSaveClick() {
        if (passwordEntryName.isBlank()) {
            showInvalidInputMessage("Name can not be empty")
            return
        }

        if (passwordEntry != null) {
            val updatedPasswordEntry = passwordEntry!!.copy(name = passwordEntryName, username = passwordEntryUsername, password = passwordEntryPassword, URL = passwordEntryURL)
            updatePasswordEntry(updatedPasswordEntry)
        } else {
            val newPasswordEntry = PasswordEntry(name = passwordEntryName, username = passwordEntryUsername, password = passwordEntryPassword, URL = passwordEntryURL)
            createPasswordEntry(newPasswordEntry)
        }
    }

    private fun createPasswordEntry(passwordEntry: PasswordEntry) = viewModelScope.launch {
        passwordDao.insert(passwordEntry)
        editEntryEventChannel.send(EditEntryEvent.NavigateBackWithResult(ADD_ENTRY_RESULT_OK))
    }

    private fun updatePasswordEntry(passwordEntry: PasswordEntry) = viewModelScope.launch {
        passwordDao.update(passwordEntry)
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