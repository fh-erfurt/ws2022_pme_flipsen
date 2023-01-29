package de.fhe.ai.flipsen.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "password_entry")
@Parcelize
data class PasswordEntry(
    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name = "password")
    var password: String = "",

    @ColumnInfo(name = "URL")
    var URL: String = "",

    @ColumnInfo(name = "group_id")
    var groupId: Int = 0,

    @ColumnInfo(name = "account_id")
    var accountId: Int = 0,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @Ignore
    var group: PasswordGroup = PasswordGroup(),
    ) : Parcelable