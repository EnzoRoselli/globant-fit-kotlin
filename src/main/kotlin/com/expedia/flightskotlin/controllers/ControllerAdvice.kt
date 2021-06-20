package com.expedia.flightskotlin.controllers

import com.expedia.flightskotlin.exceptions.ErrorMessage
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.sql.Timestamp

@RestControllerAdvice
class ControllerAdvice {

    val logger = LoggerFactory.getLogger(javaClass.simpleName)

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun badRequest(ex:Exception, request:WebRequest): ResponseEntity<ErrorMessage> {
        logger.error(ex.message)
        return ResponseEntity(ErrorMessage(Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.reasonPhrase,ex.message, request.getDescription(false)), HttpStatus.BAD_REQUEST)
    }

}