package me.ctossato.githubrepos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import me.ctossato.githubrepos.R
import me.ctossato.githubrepos.core.createDialog
import me.ctossato.githubrepos.core.createProgressDialog
import me.ctossato.githubrepos.core.hideSoftKeyboard
import me.ctossato.githubrepos.databinding.ActivityMainBinding
import me.ctossato.githubrepos.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private val viewModel by viewModel<MainViewModel>()

    private val gitRepoAdapter by lazy {GitRepositoriesListAdapter()}

    private val dialog by lazy { createProgressDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.rvGitRepoList.adapter = gitRepoAdapter

        viewModel.gitRepositories.observe(this) {
            when(it){
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog{
                        setMessage(R.string.msg_error_repo_list)
                    }.show()
                }
                MainViewModel.State.Loading -> {
                    dialog.show()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    if (it.list.isEmpty()) {
                        createDialog{
                            setMessage(R.string.msg_empty_repo_list)
                        }.show()
                    }
                    gitRepoAdapter.submitList(it.list)
                }
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)

    }

    // metodos do toolbar de search onQueryTextSubmit e onQueryTextChange
    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.getDirRepositoryList(it) }
        binding.root.hideSoftKeyboard() // from Extentions.kt
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
    // FIM dos metodos do toolbar de search
}