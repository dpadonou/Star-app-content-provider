package fr.istic.mob.starapplication.repositories

import fr.istic.mob.starapplication.dao.BusRouteDAO
import fr.istic.mob.starapplication.models.BusRoutes

class BusRouteRepository(private val busRoutesDAO: BusRouteDAO) {
     suspend fun addBusRoute(busRoutes: BusRoutes){
        busRoutesDAO.insertBusRoutes(busRoutes)
    }

     suspend fun addAllBusRoutes(l:List<BusRoutes>){
         busRoutesDAO.insertAllBusRoutes(l)
    }

     fun deleteAllBusRoutes(){
        busRoutesDAO.deleteAllBusRoutes()
    }
}