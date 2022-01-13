package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import fr.istic.mob.starapplication.models.BusRoutes
import fr.istic.mob.starapplication.database.StarContract



@Dao
interface BusRouteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBusRoutes(b: BusRoutes)

    @Insert
    suspend fun insertAllBusRoutes(l:List<BusRoutes>)

    @Update
    fun updateBusRoutes(b: BusRoutes)

    @Delete
    fun deleteBusRoutes(b: BusRoutes)

    @Query("DELETE FROM "+StarContract.BusRoutes.CONTENT_PATH)
    fun deleteAllBusRoutes()

    @Query("Select * from " + StarContract.BusRoutes.CONTENT_PATH)
    fun getRouteList(): List<BusRoutes?>?

    /**
     * Select distinct busroute._id, busroute.route_short_name, busroute.route_long_name, busroute.route_color, busroute.route_text_color
     * from busroute
     * order by busroute._id;
     *
     * @return a Cursor which list the result from the database
     */
    @Query(
        "Select distinct " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns._ID + ", " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.ROUTE_ID+", "+
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.SHORT_NAME + ", " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.LONG_NAME + ", " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.COLOR + ", " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR + ", " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.DESCRIPTION + ", " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.TYPE +
                " from " + StarContract.BusRoutes.CONTENT_PATH +
                " order by " + StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns._ID
    )
    fun getRouteListCursor(): Cursor?

    /*@Query(
        ("SELECT DISTINCT " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns._ID + ", " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.ROUTE_ID+", "+
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.SHORT_NAME + ", " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.LONG_NAME + ", " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.COLOR + ", " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR + ", " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.DESCRIPTION + ", " +
                StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.TYPE +
                " FROM " + StarContract.BusRoutes.CONTENT_PATH + "," + StarContract.Trips.CONTENT_PATH + "," + StarContract.Stops.CONTENT_PATH + "," + StarContract.StopTimes.CONTENT_PATH +
                " WHERE " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME + "= :stop_name" +
                " AND " + StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns.ROUTE_ID + "= " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID +
                " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.TRIP_ID + "= " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID +
                " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + "= " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.STOP_ID +
                " ORDER BY " + StarContract.BusRoutes.CONTENT_PATH + "." + StarContract.BusRoutes.BusRouteColumns._ID)
    )*/
    @RawQuery
    fun getRoutesForStop(qry: SupportSQLiteQuery): Cursor?

    /* @Query("SELECT * FROM BusRoutes")
     fun getAllBusRoutes(): Cursor*/

    /*@Query("SELECT * FROM BusRoutes WHERE _id=:id")
    fun getBusRoutes(id: Int)*/
}