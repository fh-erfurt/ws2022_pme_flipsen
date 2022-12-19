package de.fhe.ai.flipsen.database.local

import androidx.room.*
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.model.PasswordGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

@Dao
interface PasswordDao {

    //Basic functions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(passwordEntry: PasswordEntry)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(passwordEntry: PasswordEntry)

    @Delete
    suspend fun delete(passwordEntry: PasswordEntry)

    //Queries
    @Query("SELECT * FROM password_entry WHERE account_id = :accountId")
    fun getPasswords(accountId: Int): Flow<List<PasswordEntry>>          //Flow<List<PasswordEntry>>?

    @Query("SELECT * FROM password_group WHERE id = :groupId")
    fun getPasswordGroupById(groupId: Int): PasswordGroup

    //fun getPasswordsWithGroup(accountId: Int): List<PasswordEntry> {
    //    return getPasswords(accountId)
    //        .map { password ->
    //            password.copy(group = getPasswordGroupById(password.groupId))
    //        }
    //}

}