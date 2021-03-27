package com.example.githuber.model

data class RepoResponse(
    val total_count: Int,
    val items: List<Items>?
)

data class Items(
    val id: Long,
    val full_name: String,
    val description: String,
    val pushed_at: String,
    val updated_at: String,
    val stargazers_count: Int,
    val open_issues_count: Int
)