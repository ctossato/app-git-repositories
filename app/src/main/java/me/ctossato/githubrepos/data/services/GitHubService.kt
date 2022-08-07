package me.ctossato.githubrepos.data.services

import me.ctossato.githubrepos.domain.GitRepository
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{user}/repos")
    suspend fun listGitRepositories(@Path("user") user: String): List<GitRepository>
}