package com.expedia.flightskotlin.models.entities

import com.expedia.flightskotlin.models.dtos.FlightDTO
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "flights")
data class Flight(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    val flight : String?,
    @ApiModelProperty(example = "hh:mma") val departure: LocalTime?){

    companion object { fun toFlightDTOs(flights: List<Flight>) = flights.map(::FlightDTO) }
}


