package fr.istic.mob.starapplication.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.istic.mob.starapplication.database.StarContract

@Entity(tableName = StarContract.StopTimes.CONTENT_PATH)
class StopTimes {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id:Int = 0
    @ColumnInfo(name = "trip_id")
    var tripId:String = ""
    @ColumnInfo(name = "arrival_time")
    var arrivalTime:String = ""
    @ColumnInfo(name = "departure_time")
    var departureTime:String = ""
    @ColumnInfo(name = "stop_id")
    var stopId:String = ""
    @ColumnInfo(name = "stop_sequence")
    var stopSequence:String = ""

    constructor(
        tripId: String,
        arrivalTime: String,
        departureTime: String,
        stopId: String,
        stopSequence: String
    ) {
        this.tripId = tripId
        this.arrivalTime = arrivalTime
        this.departureTime = departureTime
        this.stopId = stopId
        this.stopSequence = stopSequence
    }

    constructor()
}