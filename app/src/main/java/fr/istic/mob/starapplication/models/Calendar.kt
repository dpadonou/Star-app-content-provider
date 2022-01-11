package fr.istic.mob.starapplication.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.istic.mob.starapplication.database.StarContract

@Entity(tableName = StarContract.Calendar.CONTENT_PATH)
class Calendar {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id:Int = 0
    @ColumnInfo(name = "service_id")
    var serviceId:String = ""
    @ColumnInfo(name = "monday")
    var monday:String = ""
    @ColumnInfo(name = "tuesday")
    var tuesday:String = ""
    @ColumnInfo(name = "wednesday")
    var wednesday = ""
    var thursday:String = ""
    @ColumnInfo(name = "friday")
    var friday:String = ""
    @ColumnInfo(name = "saturday")
    var saturday:String = ""
    @ColumnInfo(name = "sunday")
    var sunday:String = ""
    @ColumnInfo(name = "start_date")
    var startDate:String = ""
    @ColumnInfo(name="end_date")
    var endDate = ""

    constructor(
        monday: String,
        tuesday: String,
        wednesday: String,
        thursday: String,
        friday: String,
        saturday: String,
        sunday: String,
        startDate: String,
        endDate: String
    ) {
        this.monday = monday
        this.tuesday = tuesday
        this.wednesday = wednesday
        this.thursday = thursday
        this.friday = friday
        this.saturday = saturday
        this.sunday = sunday
        this.startDate = startDate
        this.endDate = endDate
    }

    constructor()
}