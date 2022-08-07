package me.ctossato.githubrepos.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
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
            binding.chipRepoForks.text = item.forks.toString()
            binding.chipRepoWatchers.text = item.watchers.toString()

            Glide.with(binding.root.context)
                .load(item.ownerAvatar)
                .into(binding.ivOwnerAvatar)

            binding.ivExpand.setOnClickListener {
                binding.ivExpand.animate().rotationBy(180F).setDuration(500)
                if (binding.btOpenHtml.visibility == View.GONE) {
                    binding.chipRepoForks.visibility = View.VISIBLE
                    binding.chipRepoWatchers.visibility = View.VISIBLE
                    binding.btOpenHtml.visibility= View.VISIBLE
                    binding.tvGitRepoDescription.maxLines = 5
                    binding.tvRepoLanguages.maxLines = 2
                } else {
                    binding.btOpenHtml.visibility = View.GONE
                    binding.chipRepoForks.visibility = View.GONE
                    binding.chipRepoWatchers.visibility = View.GONE
                    binding.tvGitRepoDescription.maxLines = 2
                    binding.tvRepoLanguages.maxLines = 1
                }
            }


            // https://stackoverflow.com/questions/3004515/sending-an-intent-to-browser-to-open-specific-url
            // implementação da funcionalidade de abrir link externo
            binding.btOpenHtml.setOnClickListener { _ ->
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.html.toString())
                binding.root.context.startActivity(i)
            }
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