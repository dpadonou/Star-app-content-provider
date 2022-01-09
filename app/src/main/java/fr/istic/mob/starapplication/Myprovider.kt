package fr.istic.mob.starapplication

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.content.UriMatcher
import android.util.Log
import androidx.room.Room
import fr.istic.mob.starapplication.database.StarContract
import fr.istic.mob.starapplication.database.StarDatabase


class Myprovider : ContentProvider() {
    private lateinit var database:StarDatabase
  companion object{
      private const  val QUERY_ROUTES = 10
      private const val QUERY_STOPS = 20
      private const val QUERY_TRIPS = 30
      private const val QUERY_STOP_TIMES = 40
      private const val QUERY_CALENDAR = 50
      private const val QUERY_ROUTES_DETAILS = 60
      private const val QUERY_SEARCHED_STOPS = 70
      private const val QUERY_ROUTES_FOR_STOP = 80
      private  val URI_MATCHER = UriMatcher(UriMatcher.NO_MATCH)

     init {
          URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.BusRoutes.CONTENT_PATH, QUERY_ROUTES)
          URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Trips.CONTENT_PATH, QUERY_TRIPS)
          URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Stops.CONTENT_PATH, QUERY_STOPS)
          URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.StopTimes.CONTENT_PATH, QUERY_STOP_TIMES)
          URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Calendar.CONTENT_PATH, QUERY_CALENDAR)
          URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.RouteDetails.CONTENT_PATH, QUERY_ROUTES_DETAILS)
          URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.SearchedStops.CONTENT_PATH, QUERY_SEARCHED_STOPS)
          URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.RoutesForStop.CONTENT_PATH, QUERY_ROUTES_FOR_STOP)
      }

  }



    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        var type:String? = null
        when(URI_MATCHER.match(uri)){
            QUERY_ROUTES -> {
                type = StarContract.BusRoutes.CONTENT_ITEM_TYPE
            }
            QUERY_TRIPS -> {
                type = StarContract.Trips.CONTENT_ITEM_TYPE
            }
            QUERY_CALENDAR -> {
                type = StarContract.Calendar.CONTENT_ITEM_TYPE
            }
            QUERY_STOPS -> {
                type = StarContract.Stops.CONTENT_ITEM_TYPE
            }
            QUERY_STOP_TIMES -> {
                type = StarContract.StopTimes.CONTENT_ITEM_TYPE
            }
            QUERY_ROUTES_DETAILS -> {
                type = StarContract.RouteDetails.CONTENT_ITEM_TYPE
            }
            QUERY_SEARCHED_STOPS -> {
                type = StarContract.SearchedStops.CONTENT_ITEM_TYPE
            }
            QUERY_ROUTES_FOR_STOP -> {
                type = StarContract.RoutesForStop.CONTENT_ITEM_TYPE
            }
        }
       return type
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        database = Room.databaseBuilder(context!!,StarDatabase::class.java,StarDatabase.name).build()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
         var result:Cursor? = null
        Log.i("","${uri.toString()}")
        when(URI_MATCHER.match(uri)){
            QUERY_ROUTES -> {
                val busRouteDao = StarDatabase.getDatabase(context!!).busRoutesDAO()
                result = busRouteDao.getRouteListCursor()
            }
            QUERY_TRIPS -> {
                val tripDao = StarDatabase.getDatabase(context!!).tripsDAO()
                result = tripDao.getTripsListCursor(selectionArgs?.get(0))
            }
            QUERY_CALENDAR -> {
                val calendarDao = StarDatabase.getDatabase(context!!).calendarDAO()
                result = calendarDao.getCalendarListCursor()
            }
            QUERY_STOPS -> {
                val stopsDao = StarDatabase.getDatabase(context!!).stopsDAO()
                result = stopsDao.getStopsByLines(selectionArgs?.get(0),selectionArgs?.get(1))
            }
            QUERY_STOP_TIMES -> {
                val stopTimesDao = StarDatabase.getDatabase(context!!).stopTimesDAO()
                if(selectionArgs!!.size>2 && selectionArgs[3].isNotEmpty() ){
                    when (selectionArgs[3]){
                        StarContract.Calendar.CalendarColumns.MONDAY ->{
                            result = stopTimesDao.getStopTimeCursorMonday(selectionArgs[0],selectionArgs[1],selectionArgs[2],selectionArgs[4])
                        }
                        StarContract.Calendar.CalendarColumns.TUESDAY ->{
                            result = stopTimesDao.getStopTimeCursorTuesday(selectionArgs[0],selectionArgs[1],selectionArgs[2],selectionArgs[4])
                        }
                        StarContract.Calendar.CalendarColumns.WEDNESDAY ->{
                            result = stopTimesDao.getStopTimeCursorWenesday(selectionArgs[0],selectionArgs[1],selectionArgs[2],selectionArgs[4])
                        }
                        StarContract.Calendar.CalendarColumns.THURSDAY ->{
                            result = stopTimesDao.getStopTimeCursorThursday(selectionArgs[0],selectionArgs[1],selectionArgs[2],selectionArgs[4])
                        }
                        StarContract.Calendar.CalendarColumns.FRIDAY ->{
                            result = stopTimesDao.getStopTimeCursorFriday(selectionArgs[0],selectionArgs[1],selectionArgs[2],selectionArgs[4])
                        }
                        StarContract.Calendar.CalendarColumns.SATURDAY ->{
                            result = stopTimesDao.getStopTimeCursorSaturday(selectionArgs[0],selectionArgs[1],selectionArgs[2],selectionArgs[4])
                        }
                        StarContract.Calendar.CalendarColumns.SUNDAY ->{
                            result = stopTimesDao.getStopTimeCursorSunday(selectionArgs[0],selectionArgs[1],selectionArgs[2],selectionArgs[4])
                        }
                    }
                }
            }
            QUERY_ROUTES_DETAILS -> {
                val stopTimesDao = StarDatabase.getDatabase(context!!).stopTimesDAO()
                result = stopTimesDao.getRouteDetail(selectionArgs?.get(0),selectionArgs?.get(1))
            }
            QUERY_SEARCHED_STOPS -> {
                val stopsDao = StarDatabase.getDatabase(context!!).stopsDAO()
                result = stopsDao.getSearchedStops(selectionArgs?.get(0))
            }
            QUERY_ROUTES_FOR_STOP -> {
                val busRouteDao = StarDatabase.getDatabase(context!!).busRoutesDAO()
                result = busRouteDao.getRoutesForStop(selectionArgs?.get(0))
            }
        }
            return result
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}