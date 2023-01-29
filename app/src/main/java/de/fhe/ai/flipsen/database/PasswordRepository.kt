package de.fhe.ai.flipsen.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import de.fhe.ai.flipsen.database.local.PasswordDao
import de.fhe.ai.flipsen.database.local.PasswordDatabase
import de.fhe.ai.flipsen.model.PasswordEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Callable
import javax.inject.Inject


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

     fun getPasswords(accountId : Int) : LiveData<List<PasswordEntry>> {
        return passwordDao.getPasswords(accountId).asLiveData()
    }
}

