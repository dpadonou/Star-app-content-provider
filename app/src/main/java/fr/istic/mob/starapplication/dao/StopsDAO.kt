package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import fr.istic.mob.starapplication.models.Calendar
import fr.istic.mob.starapplication.models.Stops

@Dao
interface StopsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStops(s:Stops)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllStops(stops:ArrayList<Stops>)
    @Update
    fun updateStops(s:Stops)
    @Delete
    fun deleteStops(s:Stops)
    @Query("DELETE FROM Stops")
    fun deleteAllStops()
    @Query("SELECT * FROM Stops")
    fun getAllStops(): Cursor
    @Query("SELECT * FROM Stops WHERE id=:id")
    fun getStops(id:Int)

}