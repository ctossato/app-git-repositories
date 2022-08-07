package me.ctossato.githubrepos.data.di

import android.util.Log
import com.google.gson.GsonBuilder
import me.ctossato.githubrepos.data.repositories.GitRepoRepository
import me.ctossato.githubrepos.data.repositories.GitRepoRepositoryImpl
import me.ctossato.githubrepos.data.services.GitHubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    private const val OK_HTTP = "OkHttp"

    fun load() {
        loadKoinModules(networkModules() + repositoriesModule())
    }
    private fun networkModules(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(OK_HTTP, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                   .addInterceptor(interceptor)
                   .build()
            }
            single {
                GsonConverterFactory
                    .create(GsonBuilder().create())
            }
            single {
                createService<GitHubService>(get(), get())
            }
        }
    }

    private fun repositoriesModule(): Module {
        return module {
            single<GitRepoRepository> { GitRepoRepositoryImpl(get())}
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {
        val baseUrl = "https://api.github.com/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(factory)
            .build().create(T::class.java)
    }
}