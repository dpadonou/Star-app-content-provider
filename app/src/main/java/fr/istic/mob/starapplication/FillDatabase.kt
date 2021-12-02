package fr.istic.mob.starapplication

import android.R.attr
import android.R.attr.buttonStyleToggle
import android.provider.CalendarContract.CalendarEntity

import android.R.attr.path
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import fr.istic.mob.starapplication.models.*
import fr.istic.mob.starapplication.viewModel.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.function.Consumer
import java.util.stream.Stream

@RequiresApi(Build.VERSION_CODES.N)
class FillDatabase(var context: Context,var application: Application) {

    fun fillDatabase(){
        Log.i("","test")
        for (s:String in Utils(context).files){
            getEntitiesFromFile(s,Utils(context).directoryPath)
        }


    }

    private fun getEntitiesFromFile(fileName: String, location:String) {
        val entities: ArrayList<Any> = ArrayList()
        try {
            val f = File("$location/$fileName")
            val reader = BufferedReader(FileReader(f))
            if(reader != null){
                var lines: Stream<String> = reader.lines().skip(1)
                for(line:String in lines){
                    val fields = line.split(",").toTypedArray()
                    when(fileName) {
                        Utils(context).files[0] -> {
                            val b = BusRoutes()
                            b.color = fields[7]
                            b.description = fields[4]
                            b.shortName = fields[2]
                            b.longName = fields[3]
                            b.textColor = fields[8]
                            b.type = fields[5]
                            entities.add(b)
                            if(entities.size == 1000){

                            }
                        }
                        Utils(context).files[1] -> {
                            val c = Calendar()
                            c.monday = fields[1]
                            c.tuesday = fields[2]
                            c.wednesday = fields[3]
                            c.thursday = fields[4]
                            c.friday = fields[5]
                            c.saturday = fields[6]
                            c.sunday = fields[7]
                            c.startDate = fields[8]
                            c.endDate = fields[9]
                            entities.add(c)
                        }
                        Utils(context).files[2] -> {
                           val t = Trips()
                            t.routeId = fields[0]
                            t.serviceId = fields[1]
                            t.headSign = fields[3]
                            t.directionId = fields[5]
                            t.blockId = fields[6]
                            t.wheelChairAccessible = fields[8]
                            entities.add(t)
                        }
                        Utils(context).files[3] -> {
                           val s = Stops()
                            s.stopName = fields[2]
                            s.description = fields[3]
                            s.latitude = fields[4]
                            s.longitutde = fields[5]
                            s.wheelChairBoarding = fields[11]
                            entities.add(s)
                        }
                        Utils(context).files[4] -> {
                          val st = StopTimes()
                            st.tripId  = fields[0]
                            st.arrivalTime = fields[1]
                            st.departureTime = fields[2]
                            st.stopId = fields[3]
                            st.stopSequence = fields[4]
                        }
                    }
                }
            }
      /*      while (line != null) {
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
            } */
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    suspend fun insert(model:String, entities: ArrayList<Any>){
        when(model){
            Utils(context).files[0] -> {
                val b = BusRouteViewModel(application)
                b.deleteAllBusRoutes()
                b.addAllBusRoute(entities as ArrayList<BusRoutes>)
            }
            Utils(context).files[1] -> {
                //calendar
                val c = CalendarViewModel(application)
                c.deleteAllCalendar()
                c.addAllCalendar(entities as ArrayList<Calendar>)
            }
            Utils(context).files[2] -> {
                //trips
                val t = TripsViewModel(application)
                t.deleteAllTrips()
                t.addAllTrips(entities as ArrayList<Trips>)
            }
            Utils(context).files[3] -> {
                //stops
                val s = StopsViewModel(application)
                s.deleteAllStops()
                s.addAllBStops(entities as ArrayList<Stops>)
            }
            Utils(context).files[4] -> {
                //stops_times
                val st = StopTimesViewModel(application)
                st.deleteAllStopTimes()
                st.addAllStopTimes(entities as ArrayList<StopTimes>)
            }
        }
    }
}