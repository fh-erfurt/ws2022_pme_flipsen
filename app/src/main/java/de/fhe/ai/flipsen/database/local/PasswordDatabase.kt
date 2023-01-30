package de.fhe.ai.flipsen.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import de.fhe.ai.flipsen.database.local.dao.PasswordDao
import de.fhe.ai.flipsen.database.local.dao.PasswordFolderDao
import de.fhe.ai.flipsen.dependency_injection.ApplicationScope
import de.fhe.ai.flipsen.model.Account
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.model.PasswordFolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [PasswordEntry::class, Account::class, PasswordFolder::class], version = 1)
abstract class PasswordDatabase : RoomDatabase() {

    abstract fun passwordDao(): PasswordDao
    abstract fun passwordFolderDao(): PasswordFolderDao

    class Callback @Inject constructor(
        private val database: Provider<PasswordDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            applicationScope.launch {
                seedDatabase(
                    passwordDao = database.get().passwordDao(),
                    passwordFolderDao = database.get().passwordFolderDao()
                )
            }
        }
    }
}

suspend fun seedDatabase(passwordDao: PasswordDao, passwordFolderDao: PasswordFolderDao) {
    // First User
    passwordFolderDao.insert(PasswordFolder(id = 1, name = "Entertainment", accountId = 1))
    passwordFolderDao.insert(PasswordFolder(id = 2, name = "Development", accountId = 1))
    passwordFolderDao.insert(PasswordFolder(id = 3, name = "Banking", accountId = 1))

    passwordDao.insert(PasswordEntry(id = 1, name = "Netflix",  username = "maxispam@test.de", password = "123", URL = "netflix.com", folderId = 1))
    passwordDao.insert(PasswordEntry(id = 2, name = "YouTube",  username = "maxispam@test.de", password = "ISJDD3jJFi", URL = "youtube.com", folderId = 1))
    passwordDao.insert(PasswordEntry(id = 3, name = "Google",  username = "maxi.dev@test.de", password = "9jJo4jNi259dmfdjDde", URL = "google.com", folderId = 2))
    passwordDao.insert(PasswordEntry(id = 4, name = "Paypal",  username = "maxmustermann@test.de", password = "Meinhardt123", URL = "paypal.com", folderId = 3))

    // Second USer
    passwordFolderDao.insert(PasswordFolder(id = 4, name = "General", accountId = 2))

    passwordDao.insert(PasswordEntry(id = 5, name = "Moodle",  username = "fhler@fh-erfurt.de", password = "FlutterIstBesser", URL = "moodle.fh-erfurt.de", folderId = 4))
}