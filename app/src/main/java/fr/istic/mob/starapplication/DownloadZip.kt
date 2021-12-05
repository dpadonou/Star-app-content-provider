package fr.istic.mob.starapplication

import android.app.Application
import android.app.Dialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.HandlerThread
import android.os.IBinder
import android.os.Process
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import java.math.BigDecimal
import java.math.RoundingMode
@RequiresApi(Build.VERSION_CODES.N)
class DownloadZip(): Service() {
    var progressBarMax: Int = 100
    var progressBarProgress: Int = 0

    init {
        // Initialize PRDownloader with read and connection timeout
        val config = PRDownloaderConfig.newBuilder()
            .setReadTimeout(30000)
            .setConnectTimeout(30000)
            .build()
        PRDownloader.initialize(applicationContext, config)
    }

    //Telecharge le fichier
    fun downloadZip(url: String, fileName: String, path: String) {
        /** Telechargement ....*/
        PRDownloader.download(url, path, fileName)
            .build()
            /** Suivre la progression **/
            .setOnProgressListener {
                progressBarMax = it.totalBytes.toInt()
                progressBarProgress = it.currentBytes.toInt()
                // val d1 = Math.floorDiv( progressBarProgress,progressBarMax )
                // val q: Int = 2/5
                println("progressBarMax : ${progressBarMax} --- progressBarProgress : ${progressBarProgress}")

            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    progressBarMax = 100
                    progressBarProgress = 100
                    /** Lancer le service qui se charge de l'extraction **/
                    val target = Utils(applicationContext).directoryPath + Utils(applicationContext).separator + Utils(applicationContext).zipName
                    val intent = Intent(applicationContext, ExtractFile::class.java)
                    intent.putExtra("target",target)
                    applicationContext.startService(intent)

                    println("progressBarMax : ${progressBarMax} --- progressBarProgress : ${progressBarProgress}")
                    println("Download success full")
                    Toast.makeText(applicationContext,"Tous les fichiers ont bien été télechargé.",Toast.LENGTH_LONG).show()
                }

                override fun onError(error: com.downloader.Error?) {
                    print("Failed to download the $url")
                    Log.i("", "Erreur du telechargement")
                    Toast.makeText(applicationContext,"Erreur de telechargement",Toast.LENGTH_LONG).show()
                }
            })
    }

    override fun onCreate() {
        val thread = HandlerThread(
            "ServiceStartArguments",
            Process.THREAD_PRIORITY_BACKGROUND
        )
        thread.start()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.extras != null) {
            val url = intent.extras!!.getString("url").toString()
            val fileName = Utils(applicationContext).zipName
            val path = intent.extras!!.getString("path").toString()
            downloadZip(url, fileName, path)
        }
        return START_STICKY
    }
}