package de.fhe.ai.flipsen.view.ui.settings

import android.app.Activity
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fhe.ai.flipsen.database.local.dao.AccountDao
import de.fhe.ai.flipsen.database.local.shared_prefs.ValueStore
import de.fhe.ai.flipsen.model.Account
import de.fhe.ai.flipsen.view.ui.login.ADD_ENTRY_RESULT_OK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsPasswordViewModel @Inject constructor(
    private val accountDao: AccountDao,
    private val state : SavedStateHandle,
    private val sharedPrefs: ValueStore
) : ViewModel(){

    val accountLogin = state.get<Account>("Account")

    var accountLoginAccountPassword = state.get<String>("accountLoginAccountPassword") ?: accountLogin?.masterPassword ?: ""
        set(value) {
            field = value
            state.set("accountLoginAccountPassword", value)
        }

    var accountLoginAccountPasswordConfirm = state.get<String>("accountLoginAccountPassword") ?: accountLogin?.masterPassword ?: ""
        set(value) {
            field = value
            state.set("accountLoginAccountPassword", value)
        }

    private val settingsEventChannel = Channel<SettingsEvent>()
    val settingsEvent = settingsEventChannel.receiveAsFlow()
    val account = accountDao.getAccountById(sharedPrefs.getValue("accountId"))

    fun changePassword(account : Account) = viewModelScope.launch {
        accountDao.updateMasterPassword(accountLoginAccountPasswordConfirm, account.id)
        settingsEventChannel.send(
            SettingsPasswordViewModel.SettingsEvent.NavigateBackWithResult(
                ADD_ENTRY_RESULT_OK
            ))
    }

    fun onChangeClickPassword(): Int {
        if (accountLoginAccountPassword.isBlank()) {
            showInvalidInputMessagePassword("Bitte geben Sie eine Passwort an.")
            return 3
        }
        if (accountLoginAccountPasswordConfirm.isBlank()) {
            showInvalidInputMessagePassword("Bitte geben Sie eine Passwort an.")
            return 4
        }


        //val account = accountDao.getAccountById(sharedPrefs.getValue("accountId"))

        if (accountLoginAccountPassword ==  accountLoginAccountPasswordConfirm) {
            changePassword(account)
            return -1
        } else {
            showInvalidInputMessagePassword("Keine identischen Passwörter")
            return 2
        }
    }

    //val currentAccount = accountDao.getAccountById(sharedPrefs.getValue("accountId"))

    private fun showInvalidInputMessagePassword(text : String) = viewModelScope.launch {
        settingsEventChannel.send(SettingsPasswordViewModel.SettingsEvent.ShowInvalidInputMessage(text))
    }

    private fun showInvalidInputMessageEmail(text : String) = viewModelScope.launch {
        settingsEventChannel.send(SettingsPasswordViewModel.SettingsEvent.ShowInvalidInputMessage(text))
    }


    sealed class SettingsEvent {
        data class ShowInvalidInputMessage(val msg: String) : SettingsEvent()
        data class NavigateBackWithResult(val result: Int) : SettingsEvent()
    }
}

const val ADD_ENTRY_RESULT_OK = Activity.RESULT_FIRST_USER
const val  EDIT_ENTRY_RESULT_OK = Activity.RESULT_FIRST_USER + 1