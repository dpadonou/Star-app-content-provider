package fr.istic.mob.starapplication

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import java.math.BigDecimal
import java.math.RoundingMode
import androidx.test.core.app.ApplicationProvider.getApplicationContext

import androidx.core.content.ContextCompat.getSystemService

import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import fr.istic.mob.starapplication.database.StarDatabase


class Downloadtask: AsyncTask<Int, Int, Boolean> {
    var progressBarMax: Int = 100
    var progressBarProgress: Int = 0
    var downloaderId: Int = 0
    var context:Context
    var application:Application
    var url:String
    var fileName:String
    var path:String
     var builder: NotificationCompat.Builder
    val CHANNEL_ID = "channel1"
     var nM: NotificationManagerCompat

    constructor(context: Context,url: String, fileName: String, path: String,application:Application) : super(){
        this.url = url
        this.fileName = fileName
        this.path = path
        this.context = context
        this.application = application
        builder = NotificationCompat.Builder(this.context,CHANNEL_ID)
        nM = NotificationManagerCompat.from(this.context)
        val config = PRDownloaderConfig.newBuilder()
            .setReadTimeout(30000)
            .setConnectTimeout(30000)
            .build()
        PRDownloader.initialize(context, config)
    }

    override fun doInBackground(vararg params: Int?): Boolean {
        //Log.i("","Debut du télechargement")
        sendProgress()
        downloadZip(url,fileName,path)
        //Log.i("","Fin du télechargement")
       return true
    }
    private fun sendProgress(){
        /** Création de la chaine pour la notification **/
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,"StarDP",importance)
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
        /**Creation de la notification en elle même **/
        val content = ""
        builder.setContentTitle(context.getString(R.string.downloading_text))
            .setColor(ContextCompat.getColor(context,R.color.teal_200))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setSilent(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content).setBigContentTitle(context.getString(R.string.downloading_text)))
            .setAutoCancel(false)
            .setProgress(100,0,false)
            .setChannelId(CHANNEL_ID)
        nM.notify(1,builder.build())
    }
    override fun onProgressUpdate(vararg values: Int?) {
        val progress = values[0]
        builder.setProgress(100,progress!!,false)

        nM.notify(1,builder.build())
    }

    override fun onPostExecute(result: Boolean?) {
       // nM.cancel(1)
       // Toast.makeText(context,"Telechargement des fichiers terminé",Toast.LENGTH_LONG).show()
    }

    private fun downloadZip(url: String, fileName: String, path: String) {
        /** Telechargement ....*/
        downloaderId = PRDownloader.download(url, path, fileName)
            .build()
            /** Suivre la progression **/
            .setOnProgressListener {
                progressBarMax = it.totalBytes.toInt()
                progressBarProgress = it.currentBytes.toInt()
                // val q: Int = 2/5
                val d = (progressBarProgress.toDouble() / progressBarMax.toDouble()) * 100
                val d1 = BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN)
                this.publishProgress(d1.toInt())
                println("progressBarMax : ${progressBarMax} --- progressBarProgress : ${progressBarProgress}")
            }
            .start(object : OnDownloadListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onDownloadComplete() {
                    progressBarMax = 100
                    progressBarProgress = 100
                    println("progressBarMax : ${progressBarMax} --- progressBarProgress : ${progressBarProgress}")
                    println("Download success full")
                    Toast.makeText(context,context.getString(R.string.download_finish), Toast.LENGTH_LONG).show()
                    nM.cancel(1)
                   Utils.displayDialogMessage(context.getString(R.string.dialog_msg))
                    //Toast.makeText(context,"Telechargement des fichiers terminé",Toast.LENGTH_LONG).show()
                    DatabaseFill.scheduleJob(context)
                }

                override fun onError(error: com.downloader.Error?) {
                    Log.i("", context.getString(R.string.download_error_text))
                    Toast.makeText(context,context.getString(R.string.download_error_text), Toast.LENGTH_LONG).show()
                    nM.cancel(1)
                    //Toast.makeText(context,"Telechargement des fichiers terminé",Toast.LENGTH_LONG).show()
                }
            })
    }
}