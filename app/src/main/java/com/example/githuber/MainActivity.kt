package com.example.githuber

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githuber.model.Items
import com.example.githuber.model.RepoResponse
import com.example.githuber.model.Repos
import com.example.githuber.network.ApiClient
import com.example.githuber.network.ApiInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    private var adapter = RepoAdapter()
    private var repos = Repos()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAdapter()
        getSavedRepos()

        submit.setOnClickListener {
            if(!tv_search.text.isNullOrEmpty()){
                hideKeyboard(this)
                getRepos(tv_search.text.toString())
            }
        }
        tv_saved.setOnClickListener {
            getSavedRepos()
        }
    }

    fun getSavedRepos(){
        if (!getRepos().isNullOrEmpty()){
            repos = Gson().fromJson(getRepos(), Repos::class.java)
            adapter.setRepos(repos.repos!!)
            tv_empty.visibility = GONE
        }
    }

    fun getRepos(repo: String){
        pb.visibility = VISIBLE
        val apiService: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<RepoResponse> = apiService.getRepos(repo)
        call.enqueue(object : Callback<RepoResponse?> {
            override fun onResponse(call: Call<RepoResponse?>?, response: Response<RepoResponse?>) {
                pb.visibility = GONE
                if (response.isSuccessful) {
                    adapter.setRepos(response.body()!!.items!!)
                    if(response.body()!!.items != null && response.body()!!.items!!.isNotEmpty()) {
                        tv_saved.visibility = VISIBLE
                        tv_saved.text = "Show saved repositories"
                        tv_empty.visibility = GONE
                        Toast.makeText(this@MainActivity, "Long press a repo to save it", Toast.LENGTH_LONG).show()
                    }
                    else{
                        tv_empty.visibility = VISIBLE
                        tv_empty.text = "No repositories found"
                    }
                }
                else {
                    showSnakBarMessage(root, response.message())
                }
            }
            override fun onFailure(call: Call<RepoResponse?>?, t: Throwable?) {
                pb.visibility = GONE
                showSnakBarMessage(root, t?.message)
            }
        })
    }

    private fun setAdapter(){
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.layoutManager = linearLayoutManager
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = adapter
        adapter.onItemClick = { repo ->
            val list = ArrayList<Items>()
            repos.repos?.let {
                list.addAll(it)
            }
            var isAlreadyAdded = false
            for (savedRepo in list){
                if(savedRepo.id == repo!!.id){
                    isAlreadyAdded = true
                    break
                }
            }
            if(!isAlreadyAdded)
                list.add(repo!!)
            repos.repos = list
            saveRepo(repos)
            if(!isAlreadyAdded)
                Toast.makeText(this@MainActivity, "Saved", Toast.LENGTH_SHORT).show()
        }
    }

}