package de.fhe.ai.flipsen.view.ui.vault

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.fhe.ai.flipsen.R
import de.fhe.ai.flipsen.databinding.ItemPasswordEntryBinding
import de.fhe.ai.flipsen.model.PasswordEntry

class PasswordEntryAdapter : ListAdapter<PasswordEntry, PasswordEntryAdapter.PasswordEntryViewHolder>(DiffCallback()) {

    var onItemClick: ((PasswordEntry) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordEntryViewHolder {
        val binding = ItemPasswordEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PasswordEntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasswordEntryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(getItem(position))
        }
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

