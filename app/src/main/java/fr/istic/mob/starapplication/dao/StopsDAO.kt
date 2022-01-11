package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import fr.istic.mob.starapplication.models.Stops
import fr.istic.mob.starapplication.database.StarContract

@Dao
interface StopsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStops(s:Stops)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllStops(stops:List<Stops>)
    @Update
    suspend fun updateStops(s:Stops)
    @Delete
    suspend fun deleteStops(s:Stops)

    @Query("DELETE FROM " + StarContract.Stops.CONTENT_PATH)
    fun deleteAll()
    /*@Query("DELETE FROM Stops")
    suspend fun deleteAllStops()*/

    @Query("Select * from " + StarContract.Stops.CONTENT_PATH)
    fun getStopList(): List<Stops?>?

    @Query("Select * from " + StarContract.Stops.CONTENT_PATH)
    fun getStopListCursor(): Cursor?


    /**
     * A big join request which returns all the stops by routes and the headsign of the trip.
     *
     * select distinct stop.stop_name, stop.stop_id, trip.trip_headsign, trip.direction_id
     * from stop, stoptime, trip
     * where trip._id = stoptime.trip_id
     * and stoptime.stop_id = stop._id
     * and trip.route_id = :route_id
     * and trip.direction_id = :direction_id
     * order by stoptime.departure_time;
     *
     * @param routeId     id of the chosen route
     * @param directionId 0 or 1
     * @return a cursor
     */
    @Query(
        "SELECT DISTINCT " +
                StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns._ID + ", " +
                StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.STOP_ID + ", " +
                StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME + ", " +
                StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.DESCRIPTION + ", " +
                StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.LATITUDE + ", " +
                StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.LONGITUDE + ", " +
                StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.WHEELCHAIR_BOARDING + ", " +
                StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", " +
                StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID +
                " FROM " + StarContract.Stops.CONTENT_PATH + ", " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH +
                " WHERE " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID + " = " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID +
                " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns._ID +
                " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :routeId" +
                " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :directionId" +
                " ORDER BY " + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME
    )
    fun getStopsByLines(routeId: String?, directionId: String?): Cursor?

    @Query(
        ("SELECT DISTINCT " +
                StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME +
                " FROM " + StarContract.Stops.CONTENT_PATH +
                " WHERE " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME + " LIKE :char_sequence || '%' ORDER BY " + StarContract.Stops.StopColumns.NAME + " ASC")
    )
    fun getSearchedStops(char_sequence: String?): Cursor?

}