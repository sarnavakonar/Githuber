package com.example.githuber

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.githuber.model.Repos
import com.example.githuber.util.Utils
import com.google.android.material.snackbar.Snackbar


open class BaseActivity: AppCompatActivity() {

    fun getUserPassword(): String?{
        return Utils.getUserPassword(this)
    }

    fun saveUser(username: String, password: String){
        Utils.saveUser(this, username, password)
    }

    fun getRepos(): String{
        return Utils.getRepos(this)!!
    }

    fun saveRepo(repos: Repos){
        Utils.saveRepos(this, repos)
    }

    fun showSnakBarMessage(view: View?, msg: String?) {
        Snackbar.make(view!!, msg!!, Snackbar.LENGTH_SHORT).show()
    }

    open fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}