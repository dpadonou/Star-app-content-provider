package fr.istic.mob.starapplication.repositories

import fr.istic.mob.starapplication.dao.TripsDAO
import fr.istic.mob.starapplication.models.Trips

class TripsRepository(private val tripsDAO: TripsDAO) {
    suspend fun addTrips(t:Trips){
        tripsDAO.insertTrips(t)
    }

    suspend fun addAllTrips(trips:List<Trips>){
        tripsDAO.insertAllTrips(trips)
    }
    fun deleteAllTrips(){
       tripsDAO.deleteAll()
    }
}