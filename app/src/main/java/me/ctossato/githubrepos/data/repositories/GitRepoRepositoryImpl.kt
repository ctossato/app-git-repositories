package me.ctossato.githubrepos.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.ctossato.githubrepos.data.services.GitHubService
import me.ctossato.githubrepos.domain.GitRepository


class GitRepoRepositoryImpl(private var service: GitHubService) : GitRepoRepository {
    override suspend fun listRepositories(user: String) = flow {
        val repolist = service.listGitRepositories(user)
        emit(repolist) }


}