package com.example.githuber.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.githuber.model.Repos
import com.google.gson.Gson
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    fun saveUser(context: Context, username: String, password: String){
        val preferences = getSharedPref(context)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    fun getUserPassword(context: Context): String?{
        val preferences = getSharedPref(context)
        return preferences.getString("password","")
    }

    fun getRepos(context: Context): String?{
        val preferences = getSharedPref(context)
        return preferences.getString("repos","")
    }

    fun saveRepos(context: Context, repos: Repos) {
        val preferences = getSharedPref(context)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString("repos", Gson().toJson(repos))
        editor.apply()
    }

    private fun getSharedPref(context: Context): SharedPreferences{
        return context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    }

    fun getDate(date: String): String{
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val output = SimpleDateFormat("dd MMM yyyy")

        var d: Date? = null
        var formatted = ""
        try {
            d = input.parse(date)
            formatted = output.format(d)
        } catch (e: Exception) {
            e.printStackTrace()
            formatted = "Unknown Date"
        }
        Log.i("DATE", "" + formatted)
        return formatted
    }

}