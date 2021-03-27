package com.example.githuber

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.sarnava.textwriter.TextWriter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        text_writer
            .setWidth(10f)
            .setDelay(30)
            .setColor(Color.WHITE)
            .setConfig(TextWriter.Configuration.INTERMEDIATE)
            .setSizeFactor(25f)
            .setLetterSpacing(25f)
            .setText("GITHUBER")
            .setListener {
                startAnim()
            }
            .startAnimation()

        tv_login.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun startAnim(){
        val anim = TranslateAnimation(0f, 0f, 0f, -350f)
        anim.duration = 500
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                showBottomSheet()
                if(getUserPassword().isNullOrEmpty()){
                    tv_login.text = "SIGN UP"
                }
                else{
                    tv_login.text = "LOGIN"
                }
            }
        })
        anim.fillAfter = true
        text_writer.startAnimation(anim)
    }

    private fun showBottomSheet(){
        val authBottomSheet = AuthBottomSheet(
            !getUserPassword().isNullOrEmpty(),
            object : AuthBottomSheet.SubmitListener{
                override fun submitName(name: String?, password: String) {
                    hideKeyboard(this@LoginActivity)
                    if(name == null && password == getUserPassword()){
                        goToHome()
                    }
                    else{
                        saveUser(name!!,password)
                        goToHome()
                    }
                }
            })
        authBottomSheet.show(supportFragmentManager, "")
        cl_auth.visibility = VISIBLE
    }

    fun goToHome(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}