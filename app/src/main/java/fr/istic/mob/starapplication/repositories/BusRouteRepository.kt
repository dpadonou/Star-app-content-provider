package fr.istic.mob.starapplication.repositories

import fr.istic.mob.starapplication.dao.BusRoutesDAO
import fr.istic.mob.starapplication.models.BusRoutes

class BusRouteRepository(private val busRoutesDAO: BusRoutesDAO) {
    val allBusRoutes = busRoutesDAO.getAllBusRoutes()
    suspend fun addBusRoute(busRoutes: BusRoutes){
        busRoutesDAO.insertBusRoutes(busRoutes)
    }

    suspend fun addAllBusRoutes(l:ArrayList<BusRoutes>){
         busRoutesDAO.insertAllBusRoutes(l)
    }

    fun deleteAllBusRoutes(){
        busRoutesDAO.deleteAllBusRoutes()
    }
}