package fr.istic.mob.starapplication

import android.app.*
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import android.app.NotificationManager

class Progression{
     lateinit var builder: NotificationCompat.Builder
     val CHANNEL_ID = "channel1"
     lateinit var nM:NotificationManagerCompat
     lateinit var context: Context
     init {
         builder = NotificationCompat.Builder(context,CHANNEL_ID)
         nM = NotificationManagerCompat.from(context)
     }
    constructor( context: Context,title:String,max:Int){
        this.context = context
        sendNotify(title,max)
    }
    private fun sendNotify(title:String,max:Int){
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
        builder.setContentTitle(title)
            .setColor(ContextCompat.getColor(context,R.color.teal_200))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content).setBigContentTitle(title))
            .setAutoCancel(false)
            .setProgress(max,0,false)
            .setChannelId(CHANNEL_ID)
        nM.notify(1,builder.build())
     }
}