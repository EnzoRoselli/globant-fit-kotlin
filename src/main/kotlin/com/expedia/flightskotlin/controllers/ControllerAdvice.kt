package com.expedia.flightskotlin.controllers

import com.expedia.flightskotlin.exceptions.ErrorMessage
import com.expedia.flightskotlin.exceptions.FlightNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.sql.SQLIntegrityConstraintViolationException
import java.sql.Timestamp

@RestControllerAdvice
class ControllerAdvice {

    val logger = LoggerFactory.getLogger(javaClass.simpleName)

    @ExceptionHandler(MethodArgumentTypeMismatchException::class, HttpMessageNotReadableException::class, SQLIntegrityConstraintViolationException::class)
    fun badRequest(ex:Exception, request:WebRequest): ResponseEntity<ErrorMessage> {
        logger.error(ex.message)
        return ResponseEntity(ErrorMessage(Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.reasonPhrase,ex.message, request.getDescription(false)), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(FlightNotFoundException::class)
    fun notFound(ex:Exception, request:WebRequest): ResponseEntity<ErrorMessage> {
        logger.error(ex.message)
        return ResponseEntity(ErrorMessage(Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.reasonPhrase,ex.message, request.getDescription(false)), HttpStatus.NOT_FOUND)
    }
}