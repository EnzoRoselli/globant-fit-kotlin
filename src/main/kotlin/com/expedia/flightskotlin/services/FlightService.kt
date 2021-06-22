package com.expedia.flightskotlin.services

import com.expedia.flightskotlin.models.dtos.FlightListDTO
import com.expedia.flightskotlin.models.entities.Flight.Companion.toFlightDTOs
import com.expedia.flightskotlin.repositories.FlightRepository
import org.springframework.stereotype.Service
import java.time.LocalTime

@Service
class FlightService(private val flightRepository : FlightRepository) {

    fun getFlights(departure: LocalTime?) = FlightListDTO(toFlightDTOs(
        departure?.let { flightRepository.findByDepartureHourAndDepartureMinute(it.hour, it.minute) } ?: flightRepository.findAll()))
}