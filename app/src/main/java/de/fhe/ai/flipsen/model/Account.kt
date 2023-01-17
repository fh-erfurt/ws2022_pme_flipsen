package de.fhe.ai.flipsen.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "account")
data class Account(
    @NonNull
    @ColumnInfo(name = "accountname")
    var accountname: String = "",

    @NonNull
    @ColumnInfo(name = "master_password")
    var masterPassword: String = "",

    @Ignore
    var passwordEntries: List<PasswordEntry> = listOf(),

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
) : Parcelable