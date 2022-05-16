package it.polito.wa2.g12.travelerservice.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import it.polito.wa2.g12.travelerservice.dto.UserDetailsDto
import it.polito.wa2.g12.travelerservice.entities.UserDetails
import it.polito.wa2.g12.travelerservice.repositories.TicketPurchasedRepository
import it.polito.wa2.g12.travelerservice.repositories.UserDetailsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

private data class Ticket(var sub: String, var iat: String, var exp: String, var zid: String)

@Service
class TravelerServImpl : TravelerService {

    @Autowired
    lateinit var userDet : UserDetailsRepository

    @Autowired
    lateinit var ticketsRepo : TicketPurchasedRepository

    override fun userExistsById(userId: Long): Boolean {
        return userDet.existsById(userId)
    }

    override fun myProfile(userId: Long) : String {
        val user = userDet.findById(userId)
        val job = jacksonObjectMapper()
        return job.writeValueAsString(UserDetailsDto(user.get().name, user.get().address, user.get().date_of_birth,user.get().phoneNumber))
    }

    override fun updateMyProfile(userId: Long, info: UserDetailsDto) {
        val userInfo: Optional<UserDetails> = userDet.findById(userId)
        userInfo.get().name = info.name
        userInfo.get().address = info.address
        userInfo.get().date_of_birth = info.date_of_birth
        userInfo.get().phoneNumber = info.number
        userDet.save(userInfo.get())
    }

    override fun userTickets(userId: Long): List<String> {
        val tickets: List<String> = ticketsRepo.findAllByUserDet(userId)
        val job = jacksonObjectMapper()
        val jsonTickets: MutableList<String> = mutableListOf()
        tickets.map { t ->
            val parts = t.split(",")
            //val string : String = "sub : ${parts[0]}, iat : ${parts[1]}, exp : ${parts[2]}, zid : ${parts[3]}"
            //jsonTickets.add(job.writeValueAsString(string))

            //Doesn't work well, don't know the reason
            jsonTickets.add(job.writeValueAsString(Ticket(parts[0], parts[1], parts[2], parts[3])))
        }
        return jsonTickets
    }

    @Transactional
    override fun postTicket(quantity: Int, zone: String) {
        //
    }

}