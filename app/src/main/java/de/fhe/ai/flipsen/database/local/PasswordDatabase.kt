package de.fhe.ai.flipsen.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import de.fhe.ai.flipsen.dependency_injection.ApplicationScope
import de.fhe.ai.flipsen.model.Account
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.model.PasswordGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [PasswordEntry::class, Account::class, PasswordGroup::class], version = 1)
abstract class PasswordDatabase : RoomDatabase() {

    abstract fun passwordDao(): PasswordDao

    class Callback @Inject constructor(
        private val database: Provider<PasswordDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val pDao = database.get().passwordDao()

            applicationScope.launch {
                pDao.insert(PasswordEntry("Netflix", "maxmustermann@test.de", "123", "netflix.com", 0, 0))
                pDao.insert(PasswordEntry("Google", "stevejobs@test.de", "ISJDD3jJFi", "google.com", 0, 0))
                pDao.insert(PasswordEntry("YouTube", "michaeljackson@test.com", "9jJo4jNi259dmfdjDde", "youtube.com", 0, 0))
            }
        }
    }

}