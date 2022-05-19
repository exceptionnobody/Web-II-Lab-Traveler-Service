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
import java.util.Optional

@Service
class TravelerServiceImpl : TravelerService {

    @Autowired
    lateinit var userDetRepo : UserDetailsRepository

    @Autowired
    lateinit var ticketsRepo : TicketPurchasedRepository

    override fun getUserDet(name: String) : UserInfoDTO? {
        return if (userDetRepo.findByName(name).isEmpty) null
        else userDetRepo.findByName(name).get().toDTO()
    }

    override fun getUserDetById(userId: Long): UserInfoDTO {
        return userDetRepo.findById(userId).get().toDTO()
    }

    //returns "0" if the record is created
    //returns "1" if the record is updated
    //returns "2" if the new name is already stored in the db
    override fun updateUserDet(name: String, info: UserInfoDTO): Int {
        val userInfo: Optional<UserDetails> = userDetRepo.findByName(name)
        val userDet: Optional<UserDetails> = userDetRepo.findByName(info.name)
        return if (userInfo.isEmpty) {
            userDetRepo.save(UserDetails(info.name, info.address, info.date_of_birth, info.number))
            0
        } else if (!userDet.isEmpty && userInfo.get().getId() == userDet.get().getId()){
            userInfo.get().name = info.name
            userInfo.get().address = info.address
            userInfo.get().date_of_birth = info.date_of_birth
            userInfo.get().phoneNumber = info.number
            userDetRepo.save(userInfo.get())
            1
        } else 2
    }

    private fun getTicketList(tickets: List<String>): MutableList<TicketDTO> {
        val ticketList: MutableList<TicketDTO> = mutableListOf()
        tickets.forEach { t ->
            val parts = t.split(",")
            ticketList.add(TicketDTO(parts[0].toLong(), parts[3], parts[1], parts[2], parts[4].toLong()))
        }
        return ticketList
    }

    override fun getUserTickets(name: String): List<TicketDTO>? {
        val userInfo: Optional<UserDetails> = userDetRepo.findByName(name)
        return if (userInfo.isEmpty) null
        else {
            val tickets: List<String> = ticketsRepo.findAllByUserDet(userInfo.get().getId()!!)
            getTicketList(tickets)
        }
    }

    override fun getTicketsByUserId(userId: Long): List<TicketDTO> {
        val tickets: List<String> = ticketsRepo.findAllByUserDet(userId)
        return getTicketList(tickets)
    }

    override fun createUserTickets(name: String, quantity: Int, zone: String): List<TicketDTO>? {
        val userInfo: Optional<UserDetails> = userDetRepo.findByName(name)
        return if (userInfo == null) null
        else {
            val user: UserDetails = userInfo.get()
            var x = quantity
            val newTickets: MutableList<TicketDTO> = mutableListOf()
            while (x > 0) {
                val newTicket = TicketPurchased(zone, user)
                ticketsRepo.save(newTicket)
                newTickets.add(newTicket.toDTO(newTicket.getId(), newTicket.userDet.getId()))
                x--
            }
            newTickets
        }
    }

    override fun getTravelers(): List<String> {
        return userDetRepo.findAllTravelers()
    }
}