package fr.istic.mob.starapplication.models

import androidx.room.Entity

@Entity
class BusRoutes {
    private  var id:Int =0
    private  var shortName:String = ""
    private  var longName:String =""
    private  var description:String =""
    private  var type:String = ""
    private  var color:String = ""
    private  var textColor:String = ""

}