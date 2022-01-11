package fr.istic.mob.starapplication

import android.content.Context
import android.os.Environment
import java.io.File

class Utils(var context: Context) {
     val directoryPath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()+ "/StarDP"
     val separator = "/"
     val zipName = "content.zip"
     val files = arrayOf("routes.txt","calendar.txt","trips.txt","stops.txt","stop_times.txt")

     companion object{
          private lateinit var activity:MainActivity
          fun setActivity(activity:MainActivity){
               this.activity = activity
          }
          fun stopActivity(){
               activity.finish()
          }

     }
}