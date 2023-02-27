package de.fhe.ai.flipsen.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "account")
data class Account(
    // ---- Primary Key
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    // ---- Attributes
    @ColumnInfo(name = "account_name")
    var accountName: String = "",

    @ColumnInfo(name = "master_password")
    var masterPassword: String = "",
) : Parcelable