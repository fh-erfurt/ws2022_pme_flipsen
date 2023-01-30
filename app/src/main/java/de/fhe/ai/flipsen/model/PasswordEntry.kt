package de.fhe.ai.flipsen.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "password_entry")
@Parcelize
data class PasswordEntry(
    // ---- Primary Key
    @ColumnInfo(name = "password_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,


    // ---- Attributes
    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name = "password")
    var password: String = "",

    @ColumnInfo(name = "URL")
    var URL: String = "",


    // ---- Foreign Keys
    @ColumnInfo(name = "folder_id")
    var folderId: Long = 0,



    // ---- Model relationship data
    @Ignore
    var folder: PasswordFolder = PasswordFolder(),
) : Parcelable