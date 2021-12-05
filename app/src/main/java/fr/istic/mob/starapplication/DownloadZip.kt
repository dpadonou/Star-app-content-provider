package fr.istic.mob.starapplication

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.os.Build
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

class DownloadZip(var context: Context, var application: Application) {
    var progressBarMax: Int = 100
    var progressBarProgress: Int = 0

    //var progression:Progression = Progression(context)
    var alertDialog = Dialog(context)
    private lateinit var input: ProgressBar
    private lateinit var textView: TextView
    private lateinit var textView2: TextView

    init {
        alertDialog.setCancelable(false)
        alertDialog.setTitle("Zip telechargement")
        alertDialog.setContentView(R.layout.progression)
        input = alertDialog.findViewById<ProgressBar>(R.id.progressBar)
        textView = alertDialog.findViewById<TextView>(R.id.textView)
        textView2 = alertDialog.findViewById<TextView>(R.id.textView2)
        input.isIndeterminate = false
        // Initialize PRDownloader with read and connection timeout
        val config = PRDownloaderConfig.newBuilder()
            .setReadTimeout(30000)
            .setConnectTimeout(30000)
            .build()
        PRDownloader.initialize(context, config)
    }

    //Telecharge le fichier
    @RequiresApi(Build.VERSION_CODES.N)
    fun downloadZip(url: String, fileName: String, path: String) {
        textView2.text = "Telechargement des fichiers"
        /** Telechargement ....*/
        PRDownloader.download(url, path, fileName)
            .build()
            /** Suivre la progression **/
            .setOnProgressListener {
                progressBarMax = it.totalBytes.toInt()
                progressBarProgress = it.currentBytes.toInt()
                // val d1 = Math.floorDiv( progressBarProgress,progressBarMax )
                input.max = progressBarMax
                input.progress = progressBarProgress
                // val q: Int = 2/5
                val d = (input.progress.toDouble() / input.max.toDouble()) * 100
                val d1 = BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN)
                textView.text = "$d1%"
                println("progressBarMax : ${progressBarMax} --- progressBarProgress : ${progressBarProgress}")

            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    progressBarMax = 100
                    progressBarProgress = 100
                    alertDialog.dismiss()
                    val e = ExtractFile(context)
                    val target = Utils(context).directoryPath + Utils(context).separator + Utils(context).zipName
                    e.extract(target, Utils(context).directoryPath)
                    val f = FillDatabase(context, application)
                    f.fillDatabase()
                    println("progressBarMax : ${progressBarMax} --- progressBarProgress : ${progressBarProgress}")
                    println("Download success full")
                    Toast.makeText(context,"Tous les fichiers ont bien été télechargé.",Toast.LENGTH_LONG).show()
                }

                override fun onError(error: com.downloader.Error?) {
                    print("Failed to download the $url")
                    Log.i("", "Erreur du telechargement")
                    Toast.makeText(context,"Erreur de telechargement",Toast.LENGTH_LONG).show()
                }
            })
        alertDialog.show()
    }
}