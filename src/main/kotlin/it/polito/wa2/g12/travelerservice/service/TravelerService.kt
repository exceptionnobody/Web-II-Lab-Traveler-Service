package it.polito.wa2.g12.travelerservice.service

import it.polito.wa2.g12.travelerservice.dto.UserDetailsDto
import javax.transaction.Transactional

interface TravelerService {
    fun myProfile(userId: Long): String
    fun updateMyProfile(userId: Long, info: UserDetailsDto)
    fun userExistsById(userId: Long): Boolean
    fun userTickets(userId: Long): List<String>
    fun postTicket(quantity: Int, zone: String)
}