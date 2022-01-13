package fr.istic.mob.starapplication

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.content.UriMatcher
import android.util.Log
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
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
                val qry = SimpleSQLiteQuery("SELECT DISTINCT "
                + StarContract.Trips.TripColumns.TRIP_ID + ", "
                + StarContract.Trips.TripColumns.HEADSIGN + ", "
                + StarContract.Trips.TripColumns.DIRECTION_ID + ", "
                + StarContract.Trips.TripColumns.ROUTE_ID +
                        " FROM " + StarContract.Trips.CONTENT_PATH +
                        " WHERE " + StarContract.Trips.TripColumns.ROUTE_ID + " = '${selectionArgs?.get(0)}'")
                result = tripDao.getTripsListCursor(qry)
            }
            QUERY_CALENDAR -> {
                val calendarDao = StarDatabase.getDatabase(context!!).calendarDAO()
                result = calendarDao.getCalendarListCursor()
            }
            QUERY_STOPS -> {
                val stopsDao = StarDatabase.getDatabase(context!!).stopsDAO()
                val qry = SimpleSQLiteQuery( "SELECT DISTINCT " +
                        StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns._ID + ", " +
                        StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.STOP_ID + ", " +
                        StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME + ", " +
                        StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.DESCRIPTION + ", " +
                        StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.LATITUDE + ", " +
                        StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.LONGITUDE + ", " +
                        StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.WHEELCHAIR_BOARDING + ", " +
                        StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + ", " +
                        StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", " +
                        StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID +
                        " FROM " + StarContract.Stops.CONTENT_PATH + ", " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH +
                        " WHERE " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID + " = " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID +
                        " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns._ID +
                        " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " ='${selectionArgs?.get(0)}' " +
                        " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " ='${selectionArgs?.get(1)}' " +
                        " ORDER BY " + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME)
                result = stopsDao.getStopsByLines(qry)
            }
            QUERY_STOP_TIMES -> {
                val stopTimesDao = StarDatabase.getDatabase(context!!).stopTimesDAO()
                if(selectionArgs!!.size>2 && selectionArgs[3].isNotEmpty() ){
                    when (selectionArgs[3]){
                        StarContract.Calendar.CalendarColumns.MONDAY ->{
                            val qry = SimpleSQLiteQuery("SELECT DISTINCT "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN
                                    + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                                    + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = '${selectionArgs[0]}'"
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = '${selectionArgs[1]}'"
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = '${selectionArgs[2]}'"
                                    + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.MONDAY + " = '1' "
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > '${selectionArgs[4]}'"
                                    + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                                    + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
                            result = stopTimesDao.getStopTimeCursorMonday(qry)
                        }
                        StarContract.Calendar.CalendarColumns.TUESDAY ->{
                            val qry = SimpleSQLiteQuery("SELECT DISTINCT "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN
                                    + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                                    + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = '${selectionArgs[0]}'"
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = '${selectionArgs[1]}'"
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = '${selectionArgs[2]}'"
                                    + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.TUESDAY + " = '1' "
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > '${selectionArgs[4]}'"
                                    + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                                    + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
                            result = stopTimesDao.getStopTimeCursorTuesday(qry)
                        }
                        StarContract.Calendar.CalendarColumns.WEDNESDAY ->{
                            val qry = SimpleSQLiteQuery("SELECT DISTINCT "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN
                                    + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                                    + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = '${selectionArgs[0]}'"
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = '${selectionArgs[1]}'"
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = '${selectionArgs[2]}'"
                                    + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.WEDNESDAY + " = '1' "
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > '${selectionArgs[4]}'"
                                    + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                                    + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
                            result = stopTimesDao.getStopTimeCursorWenesday(qry)
                        }
                        StarContract.Calendar.CalendarColumns.THURSDAY ->{
                            val qry = SimpleSQLiteQuery("SELECT DISTINCT "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN
                                    + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                                    + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = '${selectionArgs[0]}'"
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = '${selectionArgs[1]}'"
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = '${selectionArgs[2]}'"
                                    + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.THURSDAY + " = '1' "
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > '${selectionArgs[4]}'"
                                    + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                                    + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
                            result = stopTimesDao.getStopTimeCursorThursday(qry)
                        }
                        StarContract.Calendar.CalendarColumns.FRIDAY ->{
                            val qry = SimpleSQLiteQuery("SELECT DISTINCT "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN
                                    + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                                    + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = '${selectionArgs[0]}'"
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = '${selectionArgs[1]}'"
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = '${selectionArgs[2]}'"
                                    + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.FRIDAY + " = '1' "
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > '${selectionArgs[4]}'"
                                    + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                                    + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
                            result = stopTimesDao.getStopTimeCursorFriday(qry)
                        }
                        StarContract.Calendar.CalendarColumns.SATURDAY ->{
                            val qry = SimpleSQLiteQuery("SELECT DISTINCT "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN
                                    + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                                    + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = '${selectionArgs[0]}'"
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = '${selectionArgs[1]}'"
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = '${selectionArgs[2]}'"
                                    + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.SATURDAY + " = '1' "
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > '${selectionArgs[4]}'"
                                    + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                                    + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
                            result = stopTimesDao.getStopTimeCursorSaturday(qry)
                        }
                        StarContract.Calendar.CalendarColumns.SUNDAY ->{
                            val qry = SimpleSQLiteQuery("SELECT DISTINCT "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                                    + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                                    + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN
                                    + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                                    + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = '${selectionArgs[0]}'"
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = '${selectionArgs[1]}'"
                                    + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = '${selectionArgs[2]}'"
                                    + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.SUNDAY + " = '1' "
                                    + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > '${selectionArgs[4]}'"
                                    + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                                    + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
                            result = stopTimesDao.getStopTimeCursorSunday(qry)
                        }
                    }
                }
            }
            QUERY_ROUTES_DETAILS -> {
                val stopTimesDao = StarDatabase.getDatabase(context!!).stopTimesDAO()
                val qry = SimpleSQLiteQuery("SELECT DISTINCT "
                        + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME + ", "
                        + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME +
                        " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Stops.CONTENT_PATH +
                        " WHERE " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.STOP_ID + "=" + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID +
                        " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = '${selectionArgs?.get(0)}'" +
                        " AND " + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > '${selectionArgs?.get(1)}'" +
                        " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
                result = stopTimesDao.getRouteDetail(qry)
            }
            QUERY_SEARCHED_STOPS -> {
                val qry = SimpleSQLiteQuery("SELECT DISTINCT " +
                        StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME +
                        " FROM " + StarContract.Stops.CONTENT_PATH +
                        " WHERE " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME +
                        " LIKE '${selectionArgs?.get(0)}' || '%' ORDER BY " + StarContract.Stops.StopColumns.NAME + " ASC")
                val stopsDao = StarDatabase.getDatabase(context!!).stopsDAO()
                result = stopsDao.getSearchedStops(qry)
            }
            QUERY_ROUTES_FOR_STOP -> {
                val qry = SimpleSQLiteQuery("SELECT DISTINCT " +
                        StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns._ID + ", " +
                        StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.ROUTE_ID+", "+
                        StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.SHORT_NAME + ", " +
                        StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.LONG_NAME + ", " +
                        StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.COLOR + ", " +
                        StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR + ", " +
                        StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.DESCRIPTION + ", " +
                        StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.TYPE +
                        " FROM " + StarContract.BusRoutes.CONTENT_PATH + "," + StarContract.Trips.CONTENT_PATH + "," + StarContract.Stops.CONTENT_PATH + "," + StarContract.StopTimes.CONTENT_PATH +
                        " WHERE " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME + "= '${selectionArgs?.get(0)}'" +
                        " AND " + StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.ROUTE_ID + "= " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID +
                        " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID + "= " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID +
                        " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + "= " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.STOP_ID +
                        " ORDER BY " + StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns._ID)
                val busRouteDao = StarDatabase.getDatabase(context!!).busRoutesDAO()
                result = busRouteDao.getRoutesForStop(qry)
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