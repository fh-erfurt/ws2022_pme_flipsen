package de.fhe.ai.flipsen.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.model.PasswordFolder

data class FolderWithPasswords(
    @Embedded
    var folder: PasswordFolder,

    @Relation(parentColumn = "folder_id", entityColumn = "folder_id")
    var passwords: List<PasswordEntry>,
)

fun mergePasswordsIntoFolder(folder: PasswordFolder, passwords: List<PasswordEntry>): PasswordFolder {
    folder.passwordEntries = passwords
    passwords.forEach { passwordEntry -> passwordEntry.folder = folder }
    return folder
}
