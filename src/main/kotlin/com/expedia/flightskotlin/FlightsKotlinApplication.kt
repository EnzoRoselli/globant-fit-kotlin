package com.expedia.flightskotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FlightsKotlinApplication

fun main(args : Array<String>) {
    runApplication<FlightsKotlinApplication>(*args)
}
