package fr.istic.mob.starapplication.repository

import androidx.lifecycle.LiveData
import fr.istic.mob.starapplication.dao.BusRoutesDao
import fr.istic.mob.starapplication.entities.BusRoutes

class BusRoutesRepository(private val busRoutesDao: BusRoutesDao) {

    val getAllBusRoutes : LiveData<List<BusRoutes>> = busRoutesDao.getAllBusRoutes()

    suspend fun createBusRoutes(busRoutes: BusRoutes){
        busRoutesDao.addBusRoutes(busRoutes)
    }
}