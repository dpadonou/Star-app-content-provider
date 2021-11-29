package fr.istic.mob.starapplication.repositories

import fr.istic.mob.starapplication.dao.StopsDAO
import fr.istic.mob.starapplication.models.Calendar
import fr.istic.mob.starapplication.models.Stops

class StopsRepository(private val stopsDAO: StopsDAO) {
    val allStops = stopsDAO.getAllStops()
    suspend fun addStops(s:Stops){
        stopsDAO.insertStops(s)
    }

    suspend fun addAllStops(stops:ArrayList<Stops>){
        stopsDAO.insertAllStops(stops)
    }
    fun deleteAllStops(){
        stopsDAO.deleteAllStops()
    }
}