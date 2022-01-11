package fr.istic.mob.starapplication.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.istic.mob.starapplication.database.StarContract

@Entity(tableName = StarContract.Trips.CONTENT_PATH)
class Trips {
 @PrimaryKey(autoGenerate = true)
 @NonNull
  var id:Int = 0
 @ColumnInfo(name="trip_id")
  var tripId:String = ""
 @ColumnInfo(name = "route_id")
  var routeId:String = ""
 @ColumnInfo(name = "service_id")
  var serviceId:String = ""
 @ColumnInfo(name = "trip_headsign")
  var headSign:String = ""
 @ColumnInfo(name = "direction_id")
    var directionId:String = ""
 @ColumnInfo(name = "block_id")
    var blockId:String = ""
 @ColumnInfo(name = "wheelchair_accessible")
    var wheelChairAccessible:String = ""

    constructor(
        routeId: String,
        serviceId: String,
        headSign: String,
        directionId: String,
        blockId: String,
        wheelChairAccessible: String
    ) {
        this.routeId = routeId
        this.serviceId = serviceId
        this.headSign = headSign
        this.directionId = directionId
        this.blockId = blockId
        this.wheelChairAccessible = wheelChairAccessible
    }

    constructor()
}
