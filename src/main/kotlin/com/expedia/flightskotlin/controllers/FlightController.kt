package com.expedia.flightskotlin.controllers

import com.expedia.flightskotlin.exceptions.ErrorMessage
import com.expedia.flightskotlin.models.entities.Flight
import com.expedia.flightskotlin.services.FlightService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalTime

@RestController
@RequestMapping("/flights")
class FlightController(private val flightService : FlightService) {

    @GetMapping
    @ApiOperation(value = "Find flights by departure time",
        notes = "Provide a departure time with format 'hh:mma' to look up for flights in a range of 5 hours of the indicated time")
    @ApiResponses(value = [ApiResponse(code = 200, message = "Ok"), ApiResponse(code = 400, message = "Bad Request", response = ErrorMessage::class)])
    fun getFlights(@RequestParam @DateTimeFormat(pattern = "hh:mma") @ApiParam(required = false, example = "07:30AM") departure : LocalTime?)
        = flightService.getFlights(departure)

    @GetMapping("{id}")
    @ApiOperation(value = "Find flight by id", notes = "Provide an id to look up for a specific flight")
    @ApiResponses(value = [ApiResponse(code = 200, message = "Ok"), ApiResponse(code = 404, message = "Not Found", response = ErrorMessage::class)])
    fun getFlightById(@PathVariable id:Long) = flightService.getFlightById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new flight")
    @ApiResponses(value = [ApiResponse(code = 201, message = "Created"), ApiResponse(code = 400, message = "Bad Request", response = ErrorMessage::class)])
    fun saveFlight(@RequestBody flight: Flight) = flightService.saveFlight(flight)

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a flight by id")
    @ApiResponses(value = [ApiResponse(code = 204, message = "No Content")])
    fun deleteFlightById(@PathVariable id:Long) = flightService.deleteFlightById(id)
}