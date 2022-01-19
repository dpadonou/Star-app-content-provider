package fr.istic.mob.starapplication

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.io.File
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.widget.Toast
import androidx.core.content.getSystemService
import fr.istic.mob.starapplication.database.StarDatabase


class CheckStar : Service() {
    private var firstUrl:String = ""

    override fun onCreate() {
        val thread = HandlerThread(
            "ServiceStartArguments",
            Process.THREAD_PRIORITY_BACKGROUND
        )
        thread.start()
    }
    override fun onBind(intent: Intent): IBinder? {
       return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("","Check star lancé")
        getUrl()
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUrl() {
        val path = Utils(applicationContext).directoryPath
        val dir = File(path)
     /** Créer le repertoire si il n'existe pas **/
        if(!dir.exists()){
            dir.mkdirs()
        }
        val oldPref =this.getSharedPreferences("MyPref", 0)
        var link = ""
        val url = "https://data.explore.star.fr/api/records/1.0/search/?dataset=tco-busmetro-horaires-gtfs-versions-td&q="
        val req = JsonObjectRequest(Request.Method.GET, url, null, {
            val v = it.getJSONArray("records").get(0) as JSONObject
            link = (v.get("fields") as JSONObject).getString("url")
            var oldLink = oldPref.getString("link", null)
            if(oldLink == null){
                /** Ajout de l'url dans les preferences partagées **/
                val settings =applicationContext.getSharedPreferences("MyPref", 0)
                val editor = settings.edit()
                editor.putString("link",link)
                editor.apply()
                /** Telechargement du zip la premiere fois **/
                Log.i("link", link)
                //Toast.makeText(applicationContext,"Lancement du premier télechargement", Toast.LENGTH_LONG).show()
                notifyMyApp(applicationContext,link,path)
                /** Lancement du service qui effectue le telechargement **/
                val task = Downloadtask(this.baseContext,link,Utils(this.baseContext).zipName,path,application)
                task.execute(0)
            }else{
                notifyMyApp(applicationContext,link,path)
                 //Log.i("link", link)
                if(oldLink != link){
                    oldLink = link
                    /** Ajout du nouveau lien dans les preferneces partagées **/
                    val settings =applicationContext.getSharedPreferences("MyPref", 0)
                    val editor = settings.edit()
                    editor.putString("link",oldLink)
                    editor.apply()
                    /** Notification de l'application **/
                    notifyMyApp(applicationContext,link,path)
                }
            }
        },
        {  Log.i("",it.printStackTrace().toString())}
        )
        Volley.newRequestQueue(applicationContext).add(req)
    }

    /** Notifiez l'application d'un nouveau lien **/
    private fun notifyMyApp(context: Context, link:String,path:String){
        /** Création de la chaine pour la notification **/
        val CHANNEL_ID = "channel"
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,"StarDP",importance)
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
        /**Creation de la notification en elle même **/
        val builder = NotificationCompat.Builder(context,CHANNEL_ID)
        val content = getString(R.string.download_click)
        val title = getString(R.string.new_version)
        builder.setContentTitle(title)
            .setColor(ContextCompat.getColor(context,R.color.teal_200))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content).setBigContentTitle(title))
            .setAutoCancel(false)
            .setChannelId(CHANNEL_ID)

        val downloadIntent = Intent(applicationContext, MainActivity::class.java)
        downloadIntent.flags = Intent.FLAG_ACTIVITY_TASK_ON_HOME
        downloadIntent.putExtra("url",link)
        downloadIntent.putExtra("path",path)
        val stackBuilder = TaskStackBuilder.create(applicationContext)
        stackBuilder.addNextIntent(downloadIntent)
        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)
        val nM = NotificationManagerCompat.from(context)
        nM.notify(1,builder.build())
    }
}
