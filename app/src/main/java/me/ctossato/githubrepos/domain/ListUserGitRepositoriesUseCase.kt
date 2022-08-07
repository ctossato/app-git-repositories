package me.ctossato.githubrepos.domain

import kotlinx.coroutines.flow.Flow
import me.ctossato.githubrepos.core.UseCase
import me.ctossato.githubrepos.data.repositories.GitRepoRepository

class ListUserGitRepositoriesUseCase(private val repository: GitRepoRepository):
    UseCase<String, List<GitRepository>>() {
    override suspend fun execute(user: String): Flow<List<GitRepository>> {
        return repository.listRepositories(user)
    }

}