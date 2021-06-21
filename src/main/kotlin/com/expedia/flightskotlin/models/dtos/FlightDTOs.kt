package com.expedia.flightskotlin.models.dtos

import com.expedia.flightskotlin.models.entities.Flight
import java.time.format.DateTimeFormatter

data class FlightDTO(val flight: String?, val departure: String?){
    constructor(flight : Flight?) : this(flight?.flight, flight?.departure?.format(DateTimeFormatter.ofPattern("hh:mma"))
        ?.replace("a. m.", "AM")?.replace("p. m.", "PM"))
}

data class FlightListDTO(val flights:List<FlightDTO>)