package de.fhe.ai.flipsen.database.local.dao

import androidx.room.*
import de.fhe.ai.flipsen.model.Account
import de.fhe.ai.flipsen.model.PasswordEntry

@Dao
interface PasswordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(passwordEntry: PasswordEntry)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(passwordEntry: PasswordEntry)

    @Delete
    suspend fun delete(passwordEntry: PasswordEntry)
}