package org.d3ifcool.smartspending

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import org.d3if0031.smartspending.R
import org.d3if0031.smartspending.RencanaPengeluaranActivity


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            //start main activity
            startActivity(Intent(this@SplashScreenActivity, RencanaPengeluaranActivity::class.java))
            //finish this activity
            finish()
        },3000)
        

    }
}


