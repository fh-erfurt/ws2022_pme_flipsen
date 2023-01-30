package de.fhe.ai.flipsen.database

import de.fhe.ai.flipsen.database.local.dao.AccountDao
import de.fhe.ai.flipsen.model.Account
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val accountDao : AccountDao
) {

    fun insert(newAccount: Account) {
            accountDao.insert(newAccount)
    }

}