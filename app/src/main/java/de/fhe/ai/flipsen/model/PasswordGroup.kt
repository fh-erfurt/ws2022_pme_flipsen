package de.fhe.ai.flipsen.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "password_group")
@Parcelize
data class PasswordGroup(
    @NonNull
    @ColumnInfo(name = "name")
    var name: String = "",

    @Ignore
    var passwordEntries: List<PasswordEntry> = listOf(),

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
) : Parcelable