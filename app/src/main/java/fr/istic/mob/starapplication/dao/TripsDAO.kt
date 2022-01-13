package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
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

    /*@Query(
        "SELECT DISTINCT "
                + StarContract.Trips.TripColumns.TRIP_ID + ", "
                + StarContract.Trips.TripColumns.HEADSIGN + ", "
                + StarContract.Trips.TripColumns.DIRECTION_ID + ", "
                + StarContract.Trips.TripColumns.ROUTE_ID +
                " FROM " + StarContract.Trips.CONTENT_PATH +
                " WHERE " + StarContract.Trips.TripColumns.ROUTE_ID + " = :routeId"
    )*/
    @RawQuery
    fun getTripsListCursor(qry: SupportSQLiteQuery): Cursor?
}