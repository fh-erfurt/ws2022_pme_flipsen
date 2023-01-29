package de.fhe.ai.flipsen.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "password_entry")
@Parcelize
data class PasswordEntry(
    @NonNull
    @ColumnInfo(name = "name")
    var name: String = "",

    @NonNull
    @ColumnInfo(name = "username")
    var username: String = "",

    @NonNull
    @ColumnInfo(name = "password")
    var password: String = "",

    @NonNull
    @ColumnInfo(name = "URL")
    var URL: String = "",

    @NonNull
    @ColumnInfo(name = "group_id")
    var groupId: Int = 0,

    @NonNull
    @ColumnInfo(name = "account_id")
    var accountId: Int = 0,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @Ignore
    var group: PasswordGroup = PasswordGroup(),
    ) : Parcelable