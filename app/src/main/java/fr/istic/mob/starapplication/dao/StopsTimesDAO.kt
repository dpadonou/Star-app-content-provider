package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import fr.istic.mob.starapplication.models.Calendar
import fr.istic.mob.starapplication.models.StopTimes
import fr.istic.mob.starapplication.database.StarContract


@Dao
interface StopsTimesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStopTimes(st: StopTimes)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllStopsTimes(stopsTimes:List<StopTimes>)
    @Update
    suspend fun updateStopTimes(st: StopTimes)
    @Delete
    suspend fun deleteStopTimes(st: StopTimes)
   /* @Query("DELETE FROM StopTimes")
    suspend fun deleteAllStopsTimes()*/

    @Query("DELETE FROM " + StarContract.StopTimes.CONTENT_PATH)
    fun deleteAll()

    @Query("Select * from " + StarContract.StopTimes.CONTENT_PATH)
    fun getStopTimeList(): List<StopTimes?>?

   /* @Query(
        "SELECT DISTINCT "
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
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :routeId"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stopId"
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :directionId"
                + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.MONDAY + " = 1 "
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrivalTime"
                + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
    )*/
    @RawQuery
    fun getStopTimeCursorMonday(qry: SupportSQLiteQuery): Cursor?


   /* @Query(
        ("SELECT DISTINCT "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", "
                + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.START_DATE + ", "
                + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.END_DATE
                + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :routeId"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stopId"
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :directionId"
                + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.TUESDAY + " = 1"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrivalTime"
                + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    )*/
    @RawQuery
    fun getStopTimeCursorTuesday(qry: SupportSQLiteQuery): Cursor?

   /* @Query(
        ("SELECT DISTINCT "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", "
                + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.START_DATE + ", "
                + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.END_DATE
                + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :routeId"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stopId"
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :directionId"
                + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.WEDNESDAY + " = 1"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrivalTime"
                + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    )*/
    @RawQuery
    fun getStopTimeCursorWenesday(qry: SupportSQLiteQuery): Cursor?

   /* @Query(
        ("SELECT DISTINCT "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", "
                + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.START_DATE + ", "
                + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.END_DATE
                + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :routeId"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stopId"
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :directionId"
                + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.THURSDAY + " = 1"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrivalTime"
                + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    )*/
    @RawQuery
    fun getStopTimeCursorThursday(qry: SupportSQLiteQuery): Cursor?

   /* @Query(
        ("SELECT DISTINCT "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", "
                + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.START_DATE + ", "
                + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.END_DATE
                + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :routeId"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stopId"
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :directionId"
                + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.FRIDAY + " = 1"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrivalTime"
                + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    )*/
    @RawQuery
    fun getStopTimeCursorFriday(qry: SupportSQLiteQuery): Cursor?

    /*@Query(
        ("SELECT DISTINCT "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", "
                + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.START_DATE + ", "
                + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.END_DATE
                + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :routeId"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stopId"
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :directionId"
                + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.SATURDAY + " = 1"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrivalTime"
                + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    )*/
    @RawQuery
    fun getStopTimeCursorSaturday(qry: SupportSQLiteQuery): Cursor?

   /* @Query(
        ("SELECT DISTINCT "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + ", "
                + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
                + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", "
                + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.START_DATE + ", "
                + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.END_DATE
                + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
                + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :routeId"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stopId"
                + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :directionId"
                + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.SUNDAY + " = 1"
                + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrivalTime"
                + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
                + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    )*/
    @RawQuery
    fun getStopTimeCursorSunday(qry: SupportSQLiteQuery): Cursor?


   /* @Query(
        ("SELECT DISTINCT "
                + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME + ", "
                + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME +
                " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Stops.CONTENT_PATH +
                " WHERE " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.STOP_ID + "=" + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID +
                " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = :tripId" +
                " AND " + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrivalTime" +
                " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    )*/
    @RawQuery
    fun getRouteDetail(qry: SupportSQLiteQuery): Cursor?
}