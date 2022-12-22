package de.fhe.ai.flipsen.view.ui.vault

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.fhe.ai.flipsen.R
import de.fhe.ai.flipsen.model.PasswordEntry
import de.fhe.ai.flipsen.databinding.ItemPasswordEntryBinding

class PasswordEntryAdapter : ListAdapter<PasswordEntry, PasswordEntryAdapter.PasswordEntryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordEntryViewHolder {
        val binding = ItemPasswordEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PasswordEntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasswordEntryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class PasswordEntryViewHolder(private val binding: ItemPasswordEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(passwordEntry: PasswordEntry) {
            binding.apply {
                Picasso.get().load("https://unfair-white-tarantula.faviconkit.com/" + passwordEntry.URL + "/32").placeholder(R.drawable.ic_baseline_autorenew_24)
                    .error(R.drawable.ic_baseline_autorenew_24)
                    .into(icon)
                name.text = passwordEntry.name
                username.text = passwordEntry.username
                btnMore.setImageResource(R.drawable.ic_baseline_more_horiz_24)

                //TODO("Set itemClickListener with dropdown menu to delete, set group, rename (see: https://stackoverflow.com/questions/29424944/recyclerview-itemclicklistener-in-kotlin)")
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PasswordEntry>() {
        override fun areItemsTheSame(oldItem: PasswordEntry, newItem: PasswordEntry) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PasswordEntry, newItem: PasswordEntry) =
             oldItem == newItem
    }
}

