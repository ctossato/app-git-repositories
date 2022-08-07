package me.ctossato.githubrepos.data.repositories

import kotlinx.coroutines.flow.Flow
import me.ctossato.githubrepos.domain.GitRepository


interface GitRepoRepository {
    suspend fun listRepositories(user: String): Flow<List<GitRepository>>
}