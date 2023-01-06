package de.fhe.ai.flipsen.view.ui.vault

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import de.fhe.ai.flipsen.database.PasswordRepository
import de.fhe.ai.flipsen.database.local.PasswordDao
import de.fhe.ai.flipsen.model.PasswordEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.security.PrivateKey
import javax.inject.Inject

class VaultViewModel @ViewModelInject constructor(
    private val passwordDao: PasswordDao //TODO("Use repository instead of dao")
): ViewModel() {

    val passwordEntryList = passwordDao.getPasswords(0).asLiveData() //TODO("Implement getAccountId function")

    private val _text = MutableLiveData<String>().apply {
        value = "Tresor"
    }

    val text: LiveData<String> = _text

}