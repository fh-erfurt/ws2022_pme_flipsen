package de.fhe.ai.flipsen.database.local.dao

import androidx.room.*
import de.fhe.ai.flipsen.model.PasswordFolder
import de.fhe.ai.flipsen.model.relations.FolderWithPasswords
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordFolderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(passwordGroup: PasswordFolder)

    @Transaction
    @Query("SELECT * FROM password_folder WHERE account_id = :accountId")
    fun getPasswordFolders(accountId: Long): Flow<List<FolderWithPasswords>>

    @Query("SELECT * FROM password_folder WHERE account_id = :accountId AND name = :name")
    fun getFolderByName(accountId: Long, name: String): PasswordFolder?
}