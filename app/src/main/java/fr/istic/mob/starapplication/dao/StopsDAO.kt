package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import fr.istic.mob.starapplication.models.Calendar
import fr.istic.mob.starapplication.models.Stops

@Dao
interface StopsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStops(s:Stops)
    @Update
    fun updateStops(s:Stops)
    @Delete
    fun deleteStops(s:Stops)
    @Query("SELECT * FROM Stops")
    fun getAllStops(): Cursor
    @Query("SELECT * FROM Stops WHERE id=:id")
    fun getStops(id:Int)
}