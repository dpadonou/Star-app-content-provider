package fr.istic.mob.starapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Environment
import android.util.Log
import fr.istic.mob.starapplication.database.StarDatabase
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

          fun displayDialogMessage(str:String){
               val builder = AlertDialog.Builder(activity)
               builder.setTitle("Star1Dp message")
               builder.setMessage(str)
               builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                   //Utils.stopActivity()
               }
            builder.show()
          }

          /**Enlevez les cotes présentes sur les valeurs**/
          fun removeQuotes(str: String): String {
               return str.replace("\"", "")
          }
         /* fun getStop(){
               val b = StarDatabase.getDatabase(activity.baseContext).stopsDAO()
               val c = b.getStopsByLines("0003","1")
               Log.i("","${c.toString()}")
          }*/

     }

}