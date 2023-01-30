package de.fhe.ai.flipsen.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import de.fhe.ai.flipsen.database.local.dao.PasswordFolderDao
import de.fhe.ai.flipsen.model.PasswordFolder
import de.fhe.ai.flipsen.model.relations.mergePasswordsIntoFolder
import java.util.stream.Collectors
import javax.inject.Inject

class PasswordFolderRepository @Inject constructor(
    private val folderDao: PasswordFolderDao
) {

    private var allFoldersForUser: LiveData<List<PasswordFolder>>? = null

    fun insert(passwordFolder: PasswordFolder) {
        folderDao.insert(passwordFolder)
    }

    fun getFolderForUser(accountId : Long) : LiveData<List<PasswordFolder>> {
        if (allFoldersForUser == null) {
            allFoldersForUser = Transformations.map(
                folderDao.getPasswordFolders(accountId).asLiveData()
            ) { input ->
                input
                    .stream()
                    .map { item -> mergePasswordsIntoFolder(item.folder, item.passwords) }
                    .collect(Collectors.toList())
            }
        }

        return allFoldersForUser as LiveData<List<PasswordFolder>>
    }

    fun getFolderByName(accountId: Long, name: String): PasswordFolder? {
        return folderDao.getFolderByName(accountId, name)
    }
}
