package de.fhe.ai.flipsen.view.ui.login

import android.app.Activity
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fhe.ai.flipsen.database.local.dao.AccountDao
import de.fhe.ai.flipsen.model.Account
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val accountDao: AccountDao,
    private val state : SavedStateHandle
) : ViewModel() {

    val accountRegister = state.get<Account>("account")

    var accountRegisterAccountName = state.get<String>("accountRegisterAccountName") ?: accountRegister?.accountName ?: ""
        set(value) {
            field = value
            state.set("accountRegisterAccountName", value)
        }

    var accountRegisterMasterPassword = state.get<String>("accountRegisterMasterPassword") ?: accountRegister?.masterPassword ?: ""
        set(value) {
            field = value
            state.set("accountRegisterMasterPassword", value)
        }

    private val registerEventChannel = Channel<RegisterEvent>()
    val registerEvent = registerEventChannel.receiveAsFlow()

    fun onRegisterClick(): Int {
        if (accountRegisterAccountName.isBlank()) {
            showInvalidInputMessage("Bitte geben Sie eine Email an.")
            return 0
        }
        if (accountRegisterMasterPassword.isBlank()) {
            showInvalidInputMessage("Bitte geben Sie eine Passwort an.")
            return 1
        }

        val account = accountDao.getAccountByAccountName(accountRegisterAccountName)

        if (account != null) {
            showInvalidInputMessage("Es existiert bereits ein Account mit dieser Email.")
            return 2
        } else {
            val newRegisteredAccount = Account(accountName = accountRegisterAccountName, masterPassword = accountRegisterMasterPassword)
            createNewAccount(newRegisteredAccount)
            return -1
        }
    }

    private fun createNewAccount(newAccount : Account) = viewModelScope.launch {
        accountDao.insert(newAccount)
        registerEventChannel.send(RegisterEvent.NavigateBackWithResult(ADD_ENTRY_RESULT_OK))
    }

    private fun showInvalidInputMessage(text : String) = viewModelScope.launch {
        registerEventChannel.send(RegisterEvent.ShowInvalidInputMessage(text))
    }

    sealed class RegisterEvent {
        data class ShowInvalidInputMessage(val msg: String) : RegisterEvent()
        data class NavigateBackWithResult(val result: Int) : RegisterEvent()
    }
}

const val ADD_ENTRY_RESULT_OK = Activity.RESULT_FIRST_USER
const val  EDIT_ENTRY_RESULT_OK = Activity.RESULT_FIRST_USER + 1