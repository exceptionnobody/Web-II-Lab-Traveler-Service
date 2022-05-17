package it.polito.wa2.g12.travelerservice.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import it.polito.wa2.g12.travelerservice.dto.TicketDTO
import it.polito.wa2.g12.travelerservice.dto.UserDetailsDto
import it.polito.wa2.g12.travelerservice.entities.TicketPurchased
import it.polito.wa2.g12.travelerservice.entities.UserDetails
import it.polito.wa2.g12.travelerservice.entities.toDTO
import it.polito.wa2.g12.travelerservice.repositories.TicketPurchasedRepository
import it.polito.wa2.g12.travelerservice.repositories.UserDetailsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TravelerServImpl : TravelerService {

    @Autowired
    lateinit var userDetRepo : UserDetailsRepository

    @Autowired
    lateinit var ticketsRepo : TicketPurchasedRepository

    override fun myProfile(userId: Long) : UserDetailsDto {
        val user = userDetRepo.findById(userId).get()
        val job = jacksonObjectMapper()
        return UserDetailsDto(user.name, user.address, user.date_of_birth,user.phoneNumber)
    }

    override fun updateMyProfile(userId: Long, info: UserDetailsDto) {
        val userInfo: UserDetails = userDetRepo.findById(userId).get()
        userInfo.name = info.name
        userInfo.address = info.address
        userInfo.date_of_birth = info.date_of_birth
        userInfo.phoneNumber = info.number
        userDetRepo.save(userInfo)
    }


    override fun userTickets(userId: Long): List<TicketDTO> {
        val tickets: List<String> = ticketsRepo.findAllByUserDet(userId)
        val ticketList: MutableList<TicketDTO> = mutableListOf()
        tickets.forEach { t ->
            val parts = t.split(",")
            ticketList.add(TicketDTO(parts[0].toLong(), parts[3], parts[1], parts[2], parts[4].toLong()))
        }
        return ticketList
    }

    override fun postTickets(userId: Long, quantity: Int, zone: String): List<TicketDTO> {
        val user: UserDetails = userDetRepo.findById(userId).get()
        var x = quantity
        val newTickets: MutableList<TicketDTO> = mutableListOf()
        while (x > 0) {
            val newTicket = TicketPurchased(zone, user)
            ticketsRepo.save(newTicket)
            newTickets.add(newTicket.toDTO(newTicket.getId(), newTicket.userDet.getId()))
            x--
        }
        return newTickets
    }

}