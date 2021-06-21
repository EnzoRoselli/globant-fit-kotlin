package com.expedia.fitflights.services

import com.expedia.flightskotlin.models.entities.Flight
import com.expedia.flightskotlin.models.entities.Flight.Companion.toFlightDTOs
import com.expedia.flightskotlin.repositories.FlightRepository
import com.expedia.flightskotlin.services.FlightService
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import java.time.LocalTime

class FlightServiceTest {

    val flightRepository = mockk<FlightRepository>()
    val flightService = FlightService(flightRepository)
    val flights = listOf(Flight(1L, "Air Canada 8099", LocalTime.of(7, 30)),
            Flight(2L, "United Airline 6115", LocalTime.of(10, 30)),
            Flight(3L, "WestJet 6456", LocalTime.of(12, 30)),
            Flight(4L, "Delta 3833", LocalTime.of(15, 0)))

    @Nested
    inner class GetFlights{

        @Test
        fun `check if get flights passing departure time works`(){
            //given
            val expectedFlights = listOf(flights[0], flights[1])
            every { flightRepository.findByDepartureHourAndDepartureMinute(any(), any()) } returns expectedFlights

            //when
            val flightListDTO = flightService.getFlights(LocalTime.of(6, 30))

            //then
            verify { flightRepository.findByDepartureHourAndDepartureMinute(any(), any()) }
            flightListDTO.flights shouldBe toFlightDTOs(expectedFlights)
        }

        @Test
        fun `check if get flights without departure time works`(){
            //given
            every { flightRepository.findAll() } returns flights

            //when
            val flightListDTO = flightService.getFlights(null)

            //then
            verify { flightRepository.findAll() }
            flightListDTO.flights shouldBe toFlightDTOs(flights)
        }
    }
}