package me.ctossato.githubrepos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ctossato.githubrepos.databinding.ItemGitRepositoryBinding
import me.ctossato.githubrepos.domain.GitRepository

class GitRepositoriesListAdapter: ListAdapter<GitRepository, GitRepositoriesListAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGitRepositoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemGitRepositoryBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GitRepository){
            binding.tvGitRepoName.text = item.name
            binding.tvGitRepoDescription.text = item.description
            binding.tvRepoLanguages.text = item.languages
            binding.chipRepoStars.text = item.stars.toString()

            Glide.with(binding.root.context)
                .load(item.ownerAvatar)
                .into(binding.ivOwnerAvatar)
        }
    }

}

class DiffCallBack: DiffUtil.ItemCallback<GitRepository>(){
    override fun areItemsTheSame(oldItem: GitRepository, newItem: GitRepository): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: GitRepository, newItem: GitRepository): Boolean {
        return oldItem.id == newItem.id
    }

}