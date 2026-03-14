package com.example.job2nsda

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.job2nsda.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth


class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({
            val user = auth.currentUser
            if(user != null){
                startActivity(Intent(this, WelcomeScreen::class.java))
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        },3000)

    }
}