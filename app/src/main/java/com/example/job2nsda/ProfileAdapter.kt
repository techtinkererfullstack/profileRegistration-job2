package com.example.job2nsda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.job2nsda.databinding.ListItemBinding

class ProfileAdapter(
    private val onEditClick: (Profile) -> Unit,
    private val onDeleteClick: (Profile) -> Unit,
    private val onItemClick: (Profile) -> Unit
) : ListAdapter<Profile, ProfileAdapter.ProfileViewHolder>(ProfileDiffCallback()) {


    inner class ProfileViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: Profile) {
            binding.tvEmpName.text = profile.name
            binding.tvEmpEmail.text = profile.email
            binding.tvEmpRole.text = profile.department


            binding.btnEdit.setOnClickListener { onEditClick(profile) }
            binding.btnDelete.setOnClickListener { onDeleteClick(profile) }
            binding.root.setOnClickListener { onItemClick(profile) }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProfileViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: ProfileViewHolder, position: Int
    ) {
        holder.bind(getItem(position))
    }


    class ProfileDiffCallback : DiffUtil.ItemCallback<Profile>() {
        override fun areItemsTheSame(
            oldItem: Profile, newItem: Profile
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Profile, newItem: Profile
        ): Boolean {
            return oldItem == newItem

        }


    }

}