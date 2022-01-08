package fr.istic.mob.starapplication.repositories

import fr.istic.mob.starapplication.dao.StopsDAO
import fr.istic.mob.starapplication.models.Calendar
import fr.istic.mob.starapplication.models.Stops

class StopsRepository(private val stopsDAO: StopsDAO) {
    suspend fun addStops(s:Stops){
        stopsDAO.insertStops(s)
    }

    suspend fun addAllStops(stops:List<Stops>){
        stopsDAO.insertAllStops(stops)
    }
     fun deleteAllStops(){
        stopsDAO.deleteAll()
    }
}