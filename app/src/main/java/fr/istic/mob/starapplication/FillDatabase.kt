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
import kotlin.streams.toList

@RequiresApi(Build.VERSION_CODES.N)
class FillDatabase(var context: Context,var application: Application) {

     fun fillDatabase(){
        Log.i("","test")
        for (s:String in Utils(context).files){
            getEntitiesFromFile(s,Utils(context).directoryPath)
        }


    }

    private  fun getEntitiesFromFile(fileName: String, location:String) {
        //val entities: ArrayList<Any> = ArrayList()
        try {
            val f = File("$location/$fileName")
            val reader = BufferedReader(FileReader(f))
            if(reader != null){
                var lines: Stream<String> = reader.lines().skip(1)
                val l = lines.toList()
                var count:Int = 0
                when(fileName){
                    Utils(context).files[0] -> {
                        val entities = ArrayList<BusRoutes>()
                        val bV = BusRouteViewModel(application)
                        //bV.deleteAllBusRoutes()
                        for (line:String in l){
                            count++
                            val fields = line.split(",").toTypedArray()
                            val b = BusRoutes()
                            b.color = fields[7]
                            b.description = fields[4]
                            b.shortName = fields[2]
                            b.longName = fields[3]
                            b.textColor = fields[8]
                            b.type = fields[5]
                            entities.add(b)
                            if(entities.size == 1000 || count == l.size-1){
                                Log.i("","Ajout de moins ou de plus 1000 élements")
                                bV.addAllBusRoute(entities)
                                entities.clear()
                            }
                        }
                    }
                    Utils(context).files[1] -> {
                        //calendar
                        val entities = ArrayList<Calendar>()
                        val cV = CalendarViewModel(application)
                        //cV.deleteAllCalendar()
                        for (line:String in l){
                            count++
                            val fields = line.split(",").toTypedArray()
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
                            if(entities.size == 1000 || count == l.size-1){
                                Log.i("","Ajout de moins ou de plus 1000 élements")
                                cV.addAllCalendar(entities)
                                entities.clear()
                            }
                        }
                    }
                    Utils(context).files[2] -> {
                        //trips
                        val entities = ArrayList<Trips>()
                        val tV = TripsViewModel(application)
                        //tV.deleteAllTrips()
                        for (line:String in l){
                            count++
                            val fields = line.split(",").toTypedArray()
                            val t = Trips()
                            t.routeId = fields[0]
                            t.serviceId = fields[1]
                            t.headSign = fields[3]
                            t.directionId = fields[5]
                            t.blockId = fields[6]
                            t.wheelChairAccessible = fields[8]
                            entities.add(t)
                            if(entities.size == 1000 || count == l.size-1){
                                Log.i("","Ajout de moins ou de plus 1000 élements")
                                tV.addAllTrips(entities)
                                entities.clear()
                            }
                        }
                    }
                    Utils(context).files[3] -> {
                        //stops
                        val sV = StopsViewModel(application)
                        //sV.deleteAllStops()
                        val entities = ArrayList<Stops>()
                        for (line:String in l){
                            count++
                            val fields = line.split(",").toTypedArray()
                            val s = Stops()
                            s.stopName = fields[2]
                            s.description = fields[3]
                            s.latitude = fields[4]
                            s.longitutde = fields[5]
                            s.wheelChairBoarding = fields[11]
                            entities.add(s)
                            if(entities.size == 1000 || count == l.size-1){
                                Log.i("","Ajout de moins ou de plus 1000 élements")
                                sV.addAllBStops(entities)
                                entities.clear()
                            }
                        }
                    }
                    Utils(context).files[4] -> {
                        //stops_times
                        val stV = StopTimesViewModel(application)
                        //stV.deleteAllStopTimes()
                        val entities = ArrayList<StopTimes>()
                        for (line:String in l){
                            count++
                            val fields = line.split(",").toTypedArray()
                            val st = StopTimes()
                            st.tripId  = fields[0]
                            st.arrivalTime = fields[1]
                            st.departureTime = fields[2]
                            st.stopId = fields[3]
                            st.stopSequence = fields[4]
                            entities.add(st)
                            if(entities.size == 1000 || count == l.size-1){
                                Log.i("","Ajout de moins ou de plus 1000 élements")
                                stV.addAllStopTimes(entities)
                                entities.clear()
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /* fun insert(model:String, entities: ArrayList<Any>){
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
    }*/
}