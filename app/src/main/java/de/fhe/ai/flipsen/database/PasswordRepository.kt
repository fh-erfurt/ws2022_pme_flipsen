package de.fhe.ai.flipsen.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.database.local.PasswordDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class PasswordRepository @Inject constructor(
    private val passwordDao : PasswordDao) {

    suspend fun insert(passwordEntry : PasswordEntry) {
        withContext(Dispatchers.IO) {
            passwordDao.insert(passwordEntry)
        }
    }

    suspend fun update(passwordEntry : PasswordEntry) {
        withContext(Dispatchers.IO) {
            passwordDao.update(passwordEntry)
        }
    }

    suspend fun delete(passwordEntry : PasswordEntry) {
        withContext(Dispatchers.IO) {
            passwordDao.delete(passwordEntry)
        }
    }

    suspend fun getPasswords(accountId : Int) : LiveData<List<PasswordEntry>> {
        var passwordEntryList : LiveData<List<PasswordEntry>>
        withContext(Dispatchers.IO) {
            passwordEntryList = passwordDao.getPasswords(accountId).asLiveData()
        }
        return passwordEntryList
    }

}

