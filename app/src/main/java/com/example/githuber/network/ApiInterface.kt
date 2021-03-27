package com.example.githuber.network

import com.example.githuber.model.RepoResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("/search/repositories")
    fun getRepos(
        @Query("q") repo: String
    ): Call<RepoResponse>
}