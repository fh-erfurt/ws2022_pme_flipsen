package de.fhe.ai.flipsen.view.ui.login

import android.app.Activity
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fhe.ai.flipsen.database.local.dao.AccountDao
import de.fhe.ai.flipsen.database.local.shared_prefs.ValueStore
import de.fhe.ai.flipsen.model.Account
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountDao: AccountDao,
    private val sharedPrefs: ValueStore,
    private val state : SavedStateHandle
) : ViewModel() {

    val accountLogin = state.get<Account>("Account")

    var accountLoginAccountName = state.get<String>("accountLoginAccountName") ?: accountLogin?.accountName ?: ""
        set(value) {
            field = value
            state.set("accountLoginAccountName", value)
        }

    var accountLoginMasterPassword = state.get<String>("accountLoginMasterPassword") ?: accountLogin?.masterPassword ?: ""
        set(value) {
            field = value
            state.set("accountLoginMasterPassword", value)
        }

    private val loginEventChannel = Channel<LoginEvent>()
    val loginEvent = loginEventChannel.receiveAsFlow()

    fun onLoginClick(): Int {
        if (accountLoginAccountName.isBlank()) {
            showInvalidInputMessage("Bitte geben Sie eine Email an.")
            return 0
        }
        if (accountLoginMasterPassword.isBlank()) {
            showInvalidInputMessage("Bitte geben Sie ein Passwort an.")
            return 1
        }

        val account = accountDao.getAccountByAccountName(accountLoginAccountName)

        if (account == null) {
            showInvalidInputMessage("Fehlerhafte Zugangsdaten")
            return 3
        }

        if (accountLoginAccountName == account.accountName && accountLoginMasterPassword == account.masterPassword) {
            sharedPrefs.setValue("accountId",account.id.toLong())
            return -1
        } else {
            showInvalidInputMessage("Fehlerhafte Zugangsdaten")
            return 2
        }

    }


    private fun showInvalidInputMessage(text : String) = viewModelScope.launch {
        loginEventChannel.send(LoginEvent.ShowInvalidInputMessage(text))
    }

    sealed class LoginEvent {
        data class ShowInvalidInputMessage(val msg: String) : LoginEvent()
    }
}

