package de.fhe.ai.flipsen.view.ui.settings

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
class SettingsEmailViewModel @Inject constructor(
    private val accountDao: AccountDao,
    private val state : SavedStateHandle,
    private val sharedPrefs: ValueStore
) : ViewModel(){

    val accountLogin = state.get<Account>("Account")

    var accountLoginAccountName = state.get<String>("accountLoginAccountName") ?: accountLogin?.accountName ?: ""
        set(value) {
            field = value
            state.set("accountLoginAccountName", value)
        }

    var accountLoginAccountNameConfirm = state.get<String>("accountLoginAccountName") ?: accountLogin?.accountName ?: ""
        set(value) {
            field = value
            state.set("accountLoginAccountName", value)
        }

    private val settingsEventChannel = Channel<SettingsEvent>()
    val settingsEvent = settingsEventChannel.receiveAsFlow()
    val account = accountDao.getAccountById(sharedPrefs.getValue("accountId"))

    fun changeEmail(account : Account) = viewModelScope.launch {
        accountDao.updateAccountName(accountLoginAccountNameConfirm, account.id)
        settingsEventChannel.send(
            SettingsEmailViewModel.SettingsEvent.NavigateBackWithResult(
                ADD_ENTRY_RESULT_OK
            ))
    }

    fun onChangeClickEmail(): Int {
         if (accountLoginAccountName.isBlank()) {
             showInvalidInputMessageEmail("Bitte geben Sie eine Email-Adresse an.")
             return 1
         }
         if (accountLoginAccountNameConfirm.isBlank()) {
             showInvalidInputMessageEmail("Bitte geben Sie eine Email-Adresse an.")
             return 2
         }

        //val account = accountDao.getAccountById(sharedPrefs.getValue("accountId"))

         if (accountLoginAccountName ==  accountLoginAccountNameConfirm) {
             changeEmail(account)
             return -2

         } else {
             showInvalidInputMessagePassword("Keine identischen Email-Adressen")
             return 2
         }
    }

    //val currentAccount = accountDao.getAccountById(sharedPrefs.getValue("accountId"))

    private fun showInvalidInputMessagePassword(text : String) = viewModelScope.launch {
        settingsEventChannel.send(SettingsEmailViewModel.SettingsEvent.ShowInvalidInputMessage(text))
    }

    private fun showInvalidInputMessageEmail(text : String) = viewModelScope.launch {
        settingsEventChannel.send(SettingsEmailViewModel.SettingsEvent.ShowInvalidInputMessage(text))
    }


    sealed class SettingsEvent {
        data class ShowInvalidInputMessage(val msg: String) : SettingsEvent()
        data class NavigateBackWithResult(val result: Int) : SettingsEvent()
    }
}
