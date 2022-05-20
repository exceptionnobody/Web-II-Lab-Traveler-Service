package it.polito.wa2.g12.travelerservice.service.implementation

import io.jsonwebtoken.Jwts
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
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.SecretKey

@Service
class TravelerServiceImpl : TravelerService {

    @Autowired
    lateinit var userDetRepo : UserDetailsRepository
    @Autowired
    lateinit var ticketsRepo : TicketPurchasedRepository
    @Autowired
    lateinit var secretKey: SecretKey

    override fun getUserDet(name: String) : UserInfoDTO? {
        return if (userDetRepo.findByName(name).isEmpty) null
        else userDetRepo.findByName(name).get().toDTO()
    }

    override fun getUserDetById(userId: Long): UserInfoDTO? {
        val user = userDetRepo.findById(userId)
        return if (user.isEmpty) null
        else user.get().toDTO()
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
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            val exp = formatter.parse(parts[2]).time
            val iat = formatter.parse(parts[1]).time
            val claims = mapOf<String,Any>("sub" to parts[0].toLong(), "exp" to exp,"vz" to parts[3], "iat" to iat)
            val jws= Jwts.builder().setClaims(claims).signWith(secretKey).compact()
            ticketList.add(TicketDTO(parts[0].toLong(), parts[3], parts[1], parts[2],jws))
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

    override fun getTicketsByUserId(userId: Long): List<TicketDTO>? {
        return if (userDetRepo.findById(userId) == null) null
        else {
            val tickets: List<String> = ticketsRepo.findAllByUserDet(userId)
            getTicketList(tickets)
        }
    }

    override fun createUserTickets(name: String, quantity: Int, zone: String): List<TicketDTO>? {
        val userInfo: Optional<UserDetails> = userDetRepo.findByName(name)
        return if (userInfo == null) null
        else {
            val user: UserDetails = userInfo.get()
            var x = quantity
            val newTickets: MutableList<TicketDTO> = mutableListOf()
            while (x > 0) {
                var newTicket = TicketPurchased(zone, user)
                newTicket=ticketsRepo.save(newTicket)
                val exp = newTicket.deadline.time
                val iat = newTicket.issuedAt.time
                val claims = mapOf<String,Any>("sub" to newTicket.getId()!!, "exp" to exp,"vz" to newTicket.zone, "iat" to iat)
                val jws= Jwts.builder().setClaims(claims).signWith(secretKey).compact()
                newTickets.add(newTicket.toDTO(newTicket.getId(),jws))
                x--
            }
            newTickets
        }
    }

    override fun getTravelers(): List<String> {
        return userDetRepo.findAllTravelers()
    }
}