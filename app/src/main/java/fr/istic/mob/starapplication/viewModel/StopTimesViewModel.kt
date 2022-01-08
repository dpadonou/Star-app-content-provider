package fr.istic.mob.starapplication.viewModel

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.istic.mob.starapplication.database.StarDatabase
import fr.istic.mob.starapplication.models.BusRoutes
import fr.istic.mob.starapplication.models.StopTimes
import fr.istic.mob.starapplication.repositories.StopsTimesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StopTimesViewModel(application: Application):AndroidViewModel(application) {
    private val repository: StopsTimesRepository

    init {
        val stopTimesDAO = StarDatabase.getDatabase(application).stopTimesDAO()
        repository = StopsTimesRepository(stopTimesDAO)
    }

    fun addStopTimes(st:StopTimes){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStopsTimes(st)
        }
    }
     fun addAllStopTimes(stopsTimes: List<StopTimes>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAllStopsTimes(stopsTimes)
        }
    }
    fun deleteAllStopTimes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllStopsTimes()
        }
    }
}