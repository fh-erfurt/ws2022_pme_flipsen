package de.fhe.ai.flipsen.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import de.fhe.ai.flipsen.dependency_injection.ApplicationScope
import de.fhe.ai.flipsen.model.Account
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.model.PasswordGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [PasswordEntry::class, Account::class, PasswordGroup::class], version = 2)
abstract class PasswordDatabase : RoomDatabase() {

    abstract fun passwordDao(): PasswordDao

    class Callback @Inject constructor(
        private val database: Provider<PasswordDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().passwordDao()

            applicationScope.launch {
                dao.insert(PasswordEntry("Google", "Test1234"))
                dao.insert(PasswordEntry("Email", "Password"))
                dao.insert(PasswordEntry("Discord", "HelloWorld42"))
            }
        }
    }

}