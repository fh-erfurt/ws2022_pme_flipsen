package de.fhe.ai.flipsen.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "password_folder")
@Parcelize
data class PasswordFolder(
    // ---- Primary Key
    @ColumnInfo(name = "folder_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,


    // ---- Attributes
    @ColumnInfo(name = "name")
    var name: String = "",


    // ---- Foreign Keys
    @ColumnInfo(name = "account_id")
    var accountId: Int = 0,


    // ---- Model relationship data
    @Ignore
    var passwordEntries: List<PasswordEntry> = listOf(),
) : Parcelable