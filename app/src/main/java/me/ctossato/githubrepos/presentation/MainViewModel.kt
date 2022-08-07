package me.ctossato.githubrepos.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import me.ctossato.githubrepos.domain.GitRepository
import me.ctossato.githubrepos.domain.ListUserGitRepositoriesUseCase

class MainViewModel(private val listUserRepositoriesUseCase: ListUserGitRepositoriesUseCase): ViewModel() {

    private val _gitRepositories = MutableLiveData<State>()
    val gitRepositories: LiveData<State> = _gitRepositories

    fun getDirRepositoryList(user: String) {
        viewModelScope.launch {
            listUserRepositoriesUseCase.execute(user)
                .onStart {
                    _gitRepositories.postValue(State.Loading)
                }.catch {
                    _gitRepositories.postValue(State.Error(it))
                }
                .collect {
                    _gitRepositories.postValue(State.Success(it))
                }

        }
    }

    sealed class State {
        object Loading: State()
        data class Success(val list: List<GitRepository>) : State()
        data class Error(val error: Throwable) : State()
    }
}