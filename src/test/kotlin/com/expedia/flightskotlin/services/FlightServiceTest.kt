package com.expedia.flightskotlin.services

import com.expedia.flightskotlin.models.entities.Flight
import com.expedia.flightskotlin.models.entities.Flight.Companion.toFlightDTOs
import com.expedia.flightskotlin.repositories.FlightRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalTime
import java.util.stream.Stream

class FlightServiceTest {

    val flightRepository = mockk<FlightRepository>()
    val flightService = FlightService(flightRepository)
    val flights = listOf(Flight(1L, "Air Canada 8099", LocalTime.of(7, 30)),
            Flight(2L, "United Airline 6115", LocalTime.of(10, 30)),
            Flight(3L, "WestJet 6456", LocalTime.of(12, 30)),
            Flight(4L, "Delta 3833", LocalTime.of(15, 0)))

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    inner class GetFlights{

        @ParameterizedTest
        @MethodSource("validDepartureTimeProvider")
        fun `check if get flights passing departure time works`(data: TestData){
            //given
            val expectedFlights = data.flights
            every { flightRepository.findByDepartureHourAndDepartureMinute(any(), any()) } returns expectedFlights

            //when
            val flightListDTO = flightService.getFlights(data.localTime)

            //then
            verify { flightRepository.findByDepartureHourAndDepartureMinute(any(), any()) }
            flightListDTO.flights shouldBe toFlightDTOs(expectedFlights)
        }

        private fun validDepartureTimeProvider() = Stream.of(
            TestData(LocalTime.of(6,30), listOf(flights[0], flights[1])),
            TestData(LocalTime.of(22,30), listOf()),
            TestData(LocalTime.of(2,30), listOf(flights[1], flights[2], flights[3]))
        )


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

    data class TestData(val localTime: LocalTime?, val flights: List<Flight>)
}