package com.example.githuber

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githuber.model.Items
import com.example.githuber.util.Utils
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.repo_item.view.*

class RepoAdapter(): RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    private var repos = listOf<Items>()
    var onItemClick: ((Items?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.repo_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = repos[position]
        holder.repo.text = repo.full_name
        holder.desc.text = repo.description
        holder.stars.text = "Stars - ${repo.stargazers_count}"
        holder.issues.text = "Issues - ${repo.open_issues_count}"
        //holder.time.text = Utils.getDate(repo.pushed_at)
        holder.time.text = repo.pushed_at.split("T")[0]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repo: TextView = itemView.tv_repo
        val desc: TextView = itemView.tv_desc
        val stars: TextView = itemView.tv_star
        val issues: TextView = itemView.tv_issues
        val time: TextView = itemView.tv_time

        init {
            itemView.rootView.setOnClickListener {
                onItemClick?.invoke(repos[adapterPosition])
            }
        }
    }

    fun setRepos(repos: List<Items>){
        this.repos = repos
        notifyDataSetChanged()
    }



}