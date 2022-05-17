package it.polito.wa2.g12.travelerservice.service.implementation

import it.polito.wa2.g12.travelerservice.dto.TicketDTO
import it.polito.wa2.g12.travelerservice.dto.UserInfoDTO
import it.polito.wa2.g12.travelerservice.entities.TicketPurchased
import it.polito.wa2.g12.travelerservice.entities.UserDetails
import it.polito.wa2.g12.travelerservice.entities.toDTO
import it.polito.wa2.g12.travelerservice.repositories.TicketPurchasedRepository
import it.polito.wa2.g12.travelerservice.repositories.UserDetailsRepository
import it.polito.wa2.g12.travelerservice.service.TravelerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TravelerServImpl : TravelerService {

    @Autowired
    lateinit var userDetRepo : UserDetailsRepository

    @Autowired
    lateinit var ticketsRepo : TicketPurchasedRepository

    override fun getUserDet(name: String) : UserInfoDTO {
        return userDetRepo.findByName(name).get().toDTO()
    }

    override fun getUserDetById(userId: Long): UserInfoDTO {
        return userDetRepo.findById(userId).get().toDTO()
    }

    override fun updateUserDet(name: String, info: UserInfoDTO) {
        val userInfo: UserDetails = userDetRepo.findByName(name).get()
        userInfo.name = info.name
        userInfo.address = info.address
        userInfo.date_of_birth = info.date_of_birth
        userInfo.phoneNumber = info.number
        userDetRepo.save(userInfo)
    }

    private fun getTicketList(tickets: List<String>): MutableList<TicketDTO> {
        val ticketList: MutableList<TicketDTO> = mutableListOf()
        tickets.forEach { t ->
            val parts = t.split(",")
            ticketList.add(TicketDTO(parts[0].toLong(), parts[3], parts[1], parts[2], parts[4].toLong()))
        }
        return ticketList
    }

    override fun getUserTickets(name: String): List<TicketDTO> {
        val userId = userDetRepo.findByName(name).get().getId()
        val tickets: List<String> = ticketsRepo.findAllByUserDet(userId!!)
        return getTicketList(tickets)
    }

    override fun getTicketsByUserId(userId: Long): List<TicketDTO> {
        val tickets: List<String> = ticketsRepo.findAllByUserDet(userId)
        return getTicketList(tickets)
    }

    override fun createUserTickets(name: String, quantity: Int, zone: String): List<TicketDTO> {
        val userId = userDetRepo.findByName(name).get().getId()
        val user: UserDetails = userDetRepo.findById(userId!!).get()
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

    override fun getTravelers(): List<String> {
        return userDetRepo.findAllTravelers()
    }
}