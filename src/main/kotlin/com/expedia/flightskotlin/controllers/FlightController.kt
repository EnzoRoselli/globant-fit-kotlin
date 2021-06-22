package com.expedia.flightskotlin.controllers

import com.expedia.flightskotlin.exceptions.ErrorMessage
import com.expedia.flightskotlin.models.dtos.FlightListDTO
import com.expedia.flightskotlin.services.FlightService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime

@RestController
@RequestMapping("/flights")
class FlightController(private val flightService : FlightService) {

    @GetMapping
    @ApiOperation(
        value = "Find flights by departure time",
        notes = "Provide a departure time with format 'hh:mma' to look up for flights in a range of 5 hours of the indicated time")
    @ApiResponses(value = [ApiResponse(code = 200, message = "Ok"), ApiResponse(code = 400, message = "Bad Request", response = ErrorMessage::class)])
    fun getFlights(@RequestParam @DateTimeFormat(pattern = "hh:mma") @ApiParam(required = false, example = "07:30AM") departure : LocalTime?)
        = flightService.getFlights(departure)
}