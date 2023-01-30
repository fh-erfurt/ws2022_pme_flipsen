package de.fhe.ai.flipsen.database

import de.fhe.ai.flipsen.database.local.dao.PasswordDao
import de.fhe.ai.flipsen.model.PasswordEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PasswordRepository @Inject constructor(
    private val passwordDao : PasswordDao
) {

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
}

