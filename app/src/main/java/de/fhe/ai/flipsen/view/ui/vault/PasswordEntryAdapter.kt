package de.fhe.ai.flipsen.view.ui.vault

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.fhe.ai.flipsen.R
import de.fhe.ai.flipsen.databinding.ItemPasswordEntryBinding
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.model.PasswordFolder

class PasswordEntryAdapter : ListAdapter<PasswordEntry, PasswordEntryAdapter.PasswordEntryViewHolder>(DiffCallback()) {

    var onItemClick: ((PasswordEntry) -> Unit)? = null
    var onMenuClick: ((AppCompatImageButton, PasswordEntry) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordEntryViewHolder {
        val binding = ItemPasswordEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PasswordEntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasswordEntryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

        // If entry is a valid password entry add click listeners to it
        if ( currentItem.name != "") {
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(getItem(position))
            }
            val button = holder.itemView.findViewById<AppCompatImageButton>(R.id.btnMore)
            button.setOnClickListener {
                onMenuClick?.invoke(button, getItem(position))
            }
        }

    }

    class PasswordEntryViewHolder(private val binding: ItemPasswordEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(passwordEntry: PasswordEntry) {
            binding.apply {
                // If entry is a valid password entry, render it
                if ( passwordEntry.name != "") {
                    Picasso.get().load("https://www.google.com/s2/favicons?domain=" + passwordEntry.URL + "&sz=32").placeholder(R.drawable.ic_baseline_autorenew_24)
                        .error(R.drawable.ic_baseline_autorenew_24)
                        .into(icon)

                    name.text = passwordEntry.name
                    username.text = passwordEntry.username
                    btnMore.setImageResource(R.drawable.ic_baseline_more_horiz_24)

                }
                // Else it must be a header entry, so just render the folder name
                else {
                    folderName.text = passwordEntry.folder.name
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PasswordEntry>() {
        override fun areItemsTheSame(oldItem: PasswordEntry, newItem: PasswordEntry) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PasswordEntry, newItem: PasswordEntry) =
             oldItem.copy(folder = PasswordFolder()) == newItem.copy(folder = PasswordFolder())
    }
}

