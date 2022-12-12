package de.fhe.ai.flipsen.database.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.model.PasswordGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList

@Dao
abstract class PasswordDao {
    @Query("SELECT * FROM password_entry WHERE account_id = :accountId")
    abstract fun getPasswords(accountId: Int): List<PasswordEntry>

    @Query("SELECT * FROM password_group WHERE id = :groupId")
    abstract fun getPasswordGroupById(groupId: Int): PasswordGroup

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(passwordEntry: PasswordEntry)

    fun getPasswordsWithGroup(accountId: Int): List<PasswordEntry> {
        return getPasswords(accountId)
            .map { password ->
                password.copy(group = getPasswordGroupById(password.groupId))
            }
    }
}