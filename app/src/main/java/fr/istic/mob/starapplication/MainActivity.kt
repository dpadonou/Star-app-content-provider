package fr.istic.mob.starapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*val f = FillDatabase(this, application)
        f.fillDatabase()*/
        val alarm:MyAlarm = MyAlarm()
        alarm.setAlarm(this)
        val intent = intent
        if(intent.extras != null){
            val link = intent.extras!!.getString("link")
            val path = intent.extras!!.getString("path")
            val d = DownloadZip(this,application)
             d.downloadZip(link.toString(),Utils(this).zipName,path.toString())
        }

    }

    /* override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if(intent!!.extras != null){
            val link = intent.extras!!.getString("link")
            val path = intent.extras!!.getString("path")
            val d = DownloadZip(this)
            d.downloadZip(link.toString(),"content.zip",path.toString())
        }
    } */

}