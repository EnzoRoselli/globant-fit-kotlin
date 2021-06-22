package com.expedia.flightskotlin.services

import com.expedia.flightskotlin.exceptions.FlightNotFoundException
import com.expedia.flightskotlin.models.dtos.FlightListDTO
import com.expedia.flightskotlin.models.entities.Flight
import com.expedia.flightskotlin.models.entities.Flight.Companion.toFlightDTOs
import com.expedia.flightskotlin.repositories.FlightRepository
import org.springframework.stereotype.Service
import java.time.LocalTime

@Service
class FlightService(private val flightRepository : FlightRepository) {

    fun getFlights(departure: LocalTime?) = FlightListDTO(toFlightDTOs(
        departure?.let { flightRepository.findByDepartureHourAndDepartureMinute(it.hour, it.minute) } ?: flightRepository.findAll()))

    fun getFlightById(id:Long) = flightRepository.findById(id).orElseThrow{ FlightNotFoundException("User with id $id not found") }

    fun saveFlight(flight: Flight) = flightRepository.save(flight)

    fun deleteFlightById(id:Long) = try { flightRepository.deleteById(id) } catch (e:Exception) {}
}