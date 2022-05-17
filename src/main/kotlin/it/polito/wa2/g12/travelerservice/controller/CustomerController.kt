package it.polito.wa2.g12.travelerservice.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import it.polito.wa2.g12.travelerservice.dto.TicketDTO
import it.polito.wa2.g12.travelerservice.dto.UserDetailsDto
import it.polito.wa2.g12.travelerservice.entities.TicketPurchased
import it.polito.wa2.g12.travelerservice.service.TravelerServImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
class CustomerController(val travelerService: TravelerServImpl) {
    @GetMapping("/my/profile")
    fun getUserDet(
        @RequestHeader("Authorization")
        header: String
    ) : ResponseEntity<UserDetailsDto> {
        //JWT validation (fun validate Jwt)
        //JWT role check (fun getDetailsJwt)
        val userId = 2L             //this field should be returned by the getDetailsJwt fun
        //if (!validateJwt()) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val res : UserDetailsDto = travelerService.myProfile(1L)
        return ResponseEntity(res, HttpStatus.OK)
    }

    @PutMapping("/my/profile")
    fun updateUserDet(
        @RequestHeader("Authorization")
        header: String,
        @RequestBody
        body: String,
        br: BindingResult
    ) : ResponseEntity<String> {
        //JWT validation (fun validateJwt)
        //JWT role check (fun getDetailsJwt)
        val userId = 1L             //this field should be returned by the getDetailsJwt fun
        //if (!validateJwt()) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        if (br.hasErrors()) return ResponseEntity(HttpStatus.BAD_REQUEST)
        val ob = jacksonObjectMapper()
        val newInfo: UserDetailsDto = ob.readValue(body, UserDetailsDto::class.java)
        travelerService.updateMyProfile(userId, newInfo)
        return ResponseEntity("User Updated", HttpStatus.OK)
    }

    @GetMapping("/my/tickets")
    fun getTickets(
        @RequestHeader("Authorization")
        header: String
    ) : ResponseEntity<List<TicketDTO>> {
        //JWT validation (fun validate Jwt)
        //JWT role check (fun getDetailsJwt)
        val userId = 1L             //this field should be returned by the getDetailsJwt fun
        //if (!validateJwt()) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val res : List<TicketDTO> = travelerService.userTickets(userId)
        return ResponseEntity(res, HttpStatus.OK)
    }

    private data class addingTicketReq(val cmd: String, val quantity: Int, val zones: String)

    @PostMapping("/my/tickets")
    fun postTickets(
        @RequestHeader("Authorization")
        header: String,
        @RequestBody
        body: String,
        br: BindingResult
    ) : ResponseEntity<List<TicketDTO>> {
        //JWT validation (fun validate Jwt)
        //JWT role check (fun getDetailsJwt)
        val userId = 1L             //this field should be returned by the getDetailsJwt fun
        val ob = jacksonObjectMapper()
        val req = ob.readValue(body, addingTicketReq::class.java)
        //if (!validateJwt()) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        if (req.cmd != "buy_tickets"|| br.hasErrors()) return ResponseEntity(HttpStatus.BAD_REQUEST)
        val res = travelerService.postTickets(userId, req.quantity, req.zones)
        return ResponseEntity(res, HttpStatus.OK)
    }
}