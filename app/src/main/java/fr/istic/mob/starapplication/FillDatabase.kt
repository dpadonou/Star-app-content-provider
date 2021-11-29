package fr.istic.mob.starapplication

import android.R.attr
import android.R.attr.buttonStyleToggle
import android.provider.CalendarContract.CalendarEntity

import android.R.attr.path
import android.app.Application
import android.content.Context
import android.util.Log
import fr.istic.mob.starapplication.models.BusRoutes
import fr.istic.mob.starapplication.viewModel.BusRouteViewModel
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException


class FillDatabase(var context: Context,var application: Application) {

    fun fillDatabase(){
        for (s:String in Utils(context).files){
            val list = getEntitiesFromFile(s,Utils(context).directoryPath)

        }
        Log.i("","test")
    }

    fun getEntitiesFromFile(fileName: String, location:String): ArrayList<*>? {
        val entities: ArrayList<Any> = ArrayList()
        try {
            val f = File("$location/$fileName")
            val reader = BufferedReader(FileReader(f))
            var line: String? = reader.readLine()
            while (line != null) {
                val fields = line.split(",").toTypedArray()
                 when(fileName){
                     Utils(context).files[0] ->{
                         val b = BusRoutes()
                         b.color = fields[7]
                         b.description = fields[4]
                         b.shortName = fields[2]
                         b.longName = fields[3]
                         b.textColor = fields[5]
                         b.textColor = fields[8]
                         entities.add(b)
                     }

                 }
                line = reader.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return entities
    }
}