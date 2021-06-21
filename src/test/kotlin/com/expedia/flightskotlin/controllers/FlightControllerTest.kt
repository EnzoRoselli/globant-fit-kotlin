package com.expedia.flightskotlin.controllers

import com.expedia.flightskotlin.models.dtos.FlightListDTO
import com.expedia.flightskotlin.models.entities.Flight
import com.expedia.flightskotlin.models.entities.Flight.Companion.toFlightDTOs
import com.expedia.flightskotlin.services.FlightService
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.json.JacksonTester
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalTime

class FlightControllerTest {

    val flightService = mockk<FlightService>()
    val flightController = FlightController(flightService)

    val mockMvc = MockMvcBuilders.standaloneSetup(flightController)
        .setControllerAdvice(ControllerAdvice())
        .build()

    lateinit var jsonFlightListDTO: JacksonTester<FlightListDTO>

    val flights = listOf(Flight(1L, "Air Canada 8099", LocalTime.of(7, 30)),
        Flight(2L, "United Airline 6115", LocalTime.of(10, 30)),
        Flight(3L, "WestJet 6456", LocalTime.of(12, 30)),
        Flight(4L, "Delta 3833", LocalTime.of(15, 0)))

    @BeforeEach
    internal fun setUp() {
        JacksonTester.initFields(this, ObjectMapper())
    }

    @Nested
    inner class GetFlights{

        @Test
        fun `check if get flights passing departure time works`(){
            //given
            val expectedFlightListDTO = FlightListDTO(toFlightDTOs(listOf(flights[0], flights[1])))
            every { flightService.getFlights(any()) } returns expectedFlightListDTO

            //when
            val response = mockMvc.perform(MockMvcRequestBuilders.get("/flights/?departure=06:30AM")
                    .accept(MediaType.APPLICATION_JSON)).andReturn().response

            //then
            verify { flightService.getFlights(any()) }
            response.status shouldBe HttpStatus.OK.value()
            response.contentType shouldBe MediaType.APPLICATION_JSON.toString()
            response.contentAsString shouldBe jsonFlightListDTO.write(expectedFlightListDTO).json
        }

        @Test
        fun `check if get flights without departure time works`(){
            //given
            val expectedFlightListDTO = FlightListDTO(toFlightDTOs(flights))
            every { flightService.getFlights(any()) } returns expectedFlightListDTO

            //when
            val response = mockMvc.perform(MockMvcRequestBuilders.get("/flights")
                .accept(MediaType.APPLICATION_JSON)).andReturn().response

            //then
            verify { flightService.getFlights(any()) }
            response.status shouldBe HttpStatus.OK.value()
            response.contentType shouldBe MediaType.APPLICATION_JSON.toString()
            response.contentAsString shouldBe jsonFlightListDTO.write(expectedFlightListDTO).json
        }

        @Test
        fun `check exception when departure time is passed with wrong format`(){
            //when
            val response = mockMvc.perform(MockMvcRequestBuilders.get("/flights/?departure=06:30M")
                .accept(MediaType.APPLICATION_JSON)).andReturn().response

            //then
            verify(exactly = 0) { flightService.getFlights(any()) }
            response.status shouldBe HttpStatus.BAD_REQUEST.value()
            response.contentType shouldBe MediaType.APPLICATION_JSON.toString()
            response.contentAsString shouldNotBe null
        }
    }
}