package fr.istic.mob.starapplication.repositories

import fr.istic.mob.starapplication.dao.StopsTimesDAO
import fr.istic.mob.starapplication.models.StopTimes

class StopsTimesRepository(private val stopsTimesDAO:StopsTimesDAO) {
    val allStopsTimes = stopsTimesDAO.getAllStopTimes()
    suspend fun addStopsTimes(st:StopTimes){
        stopsTimesDAO.insertStopTimes(st)
    }
}