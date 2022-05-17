package it.polito.wa2.g12.travelerservice.service

import it.polito.wa2.g12.travelerservice.dto.TicketDTO
import it.polito.wa2.g12.travelerservice.dto.UserDetailsDto
import it.polito.wa2.g12.travelerservice.entities.TicketPurchased
import it.polito.wa2.g12.travelerservice.entities.UserDetails
import java.util.*
import javax.transaction.Transactional

interface TravelerService {
    fun myProfile(userId: Long): UserDetailsDto
    fun updateMyProfile(userId: Long, info: UserDetailsDto)
    fun userTickets(userId: Long): List<TicketDTO>
    fun postTickets(userId: Long, quantity: Int, zone: String): List<TicketDTO>
}