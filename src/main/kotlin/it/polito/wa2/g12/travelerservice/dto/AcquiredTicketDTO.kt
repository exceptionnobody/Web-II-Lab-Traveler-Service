package it.polito.wa2.g12.travelerservice.dto

data class AcquiredTicketDTO (
    val sub: Long,
    val iat: String,
    var validfrom: String,
    var exp: String,
    var zid: String,
    var type: String,
    var jws: String
)