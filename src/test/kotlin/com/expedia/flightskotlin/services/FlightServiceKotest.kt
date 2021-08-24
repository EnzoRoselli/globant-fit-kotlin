package com.expedia.flightskotlin.services

import com.expedia.flightskotlin.models.entities.Flight
import com.expedia.flightskotlin.repositories.FlightRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalTime

class FlightServiceKotest:FunSpec ({

    val flightRepository = mockk<FlightRepository>()
    val flightService = FlightService(flightRepository)

    context("getFlights"){

        val flights = listOf(Flight(1L, "Air Canada 8099", LocalTime.of(7, 30)),
            Flight(2L, "United Airline 6115", LocalTime.of(10, 30)),
            Flight(3L, "WestJet 6456", LocalTime.of(12, 30)),
            Flight(4L, "Delta 3833", LocalTime.of(15, 0)))

        test("check if get flights passing departure time works") {
            listOf(
                TestData(LocalTime.of(6, 30), listOf(flights[0], flights[1])),
                TestData(LocalTime.of(22, 30), listOf()),
                TestData(LocalTime.of(2, 30), listOf(flights[1], flights[2], flights[3]))
            ).forEach {
                //given
                every { flightRepository.findByDepartureHourAndDepartureMinute(any(), any()) } returns it.flights

                //when
                val flightListDTO = flightService.getFlights(it.localTime)

                //then
                verify { flightRepository.findByDepartureHourAndDepartureMinute(any(), any()) }
                flightListDTO.flights shouldBe Flight.toFlightDTOs(it.flights)
            }
        }

        test("check if get flights without departure time works") {
            //given
            every { flightRepository.findAll() } returns flights

            //when
            val flightListDTO = flightService.getFlights(null)

            //then
            verify { flightRepository.findAll() }
            flightListDTO.flights shouldBe Flight.toFlightDTOs(flights)
        }
    }

})

data class TestData(val localTime: LocalTime?, val flights: List<Flight>)
