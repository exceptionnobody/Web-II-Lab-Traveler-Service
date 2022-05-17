package it.polito.wa2.g12.travelerservice.service

import it.polito.wa2.g12.travelerservice.dto.TicketDTO
import it.polito.wa2.g12.travelerservice.dto.UserDetailsDto
interface TravelerService {
    fun getUserDet(name: String): UserDetailsDto
    fun getUserDetById(userId: Long): UserDetailsDto
    fun updateUserDet(name: String, info: UserDetailsDto)
    fun getUserTickets(name: String): List<TicketDTO>
    fun getTicketsByUserId(userId: Long): List<TicketDTO>
    fun createUserTickets(name: String, quantity: Int, zone: String): List<TicketDTO>
    fun getTravelers(): List<String>
}