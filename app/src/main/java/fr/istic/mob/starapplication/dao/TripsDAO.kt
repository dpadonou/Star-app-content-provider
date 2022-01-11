package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import fr.istic.mob.starapplication.models.Calendar
import fr.istic.mob.starapplication.models.StopTimes
import fr.istic.mob.starapplication.models.Trips
import fr.istic.mob.starapplication.database.StarContract







@Dao
interface TripsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrips(t: Trips)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllTrips(trips:List<Trips>)
    @Update
    suspend fun updateTrips(t:Trips)
    @Delete
    suspend fun deleteTrips(t:Trips)
   /* @Query("DELETE FROM Trips")
    suspend fun deleteAllTrips()*/

    @Query("DELETE FROM " + StarContract.Trips.CONTENT_PATH)
    fun deleteAll()

    /**
     * Select all element in TripEntity table
     */
    @Query("Select * from " + StarContract.Trips.CONTENT_PATH)
    fun getTripsList(): List<Trips?>?

    /**
     * A big join request which returns
     * select distinct trip.trip_headsign, trip.direction_id, trip.route_id
     * from trip
     * where trip.route_id = :route_id;
     *
     * @param routeId id of the chosen route
     * @return a Cursor which list the result from the database
     */
    @Query(
        "SELECT DISTINCT "
                + StarContract.Trips.TripColumns.TRIP_ID + ", "
                + StarContract.Trips.TripColumns.HEADSIGN + ", "
                + StarContract.Trips.TripColumns.DIRECTION_ID + ", "
                + StarContract.Trips.TripColumns.ROUTE_ID +
                " FROM " + StarContract.Trips.CONTENT_PATH +
                " WHERE " + StarContract.Trips.TripColumns.ROUTE_ID + " = :routeId"
    )
    fun getTripsListCursor(routeId: String?): Cursor?
}