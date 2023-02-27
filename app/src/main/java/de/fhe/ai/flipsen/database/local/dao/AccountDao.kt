package de.fhe.ai.flipsen.database.local.dao

import androidx.room.*
import de.fhe.ai.flipsen.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    //Basic functions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: Account)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(account: Account)

    @Delete
    fun delete(account: Account)

    //Queries

    @Query("SELECT id FROM account WHERE account_name = :accountName")
    fun getIdFromAccount(accountName: String): Int

    //Überflüssig?
    @Query("UPDATE account SET account_name = :newAccountName WHERE id = :id")
    fun updateAccountName(newAccountName: String, id: Int)

    //Überflüssig?
    @Query("UPDATE account SET master_password = :newMasterPassword WHERE id = :id")
    fun updateMasterPassword(newMasterPassword: String, id: Int)

    @Query("SELECT * FROM account WHERE account_name =:accountName")
    fun getAccountByAccountName(accountName: String): Account?

    @Query("SELECT COUNT() FROM account WHERE account_name = :accountName")
    fun count(accountName: String): Int

}