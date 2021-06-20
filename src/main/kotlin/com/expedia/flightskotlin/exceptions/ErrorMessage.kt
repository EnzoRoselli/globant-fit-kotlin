package com.expedia.flightskotlin.exceptions

import java.sql.Timestamp

data class ErrorMessage(val timestamp : Timestamp, val status : Int, val error : String, val description: String?, val path: String)