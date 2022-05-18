package it.polito.wa2.g12.travelerservice.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import it.polito.wa2.g12.travelerservice.dto.TicketDTO
import it.polito.wa2.g12.travelerservice.dto.UserInfoDTO
import it.polito.wa2.g12.travelerservice.service.implementation.TravelerServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContext
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class Controller(val travelerService: TravelerServiceImpl) {

/*
    @GetMapping("/my/profile")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun getUserDet(
        /*@RequestHeader("Authorization")
        header: String,*/
        principal: Principal
    ) : ResponseEntity<Any> {
        //JWT validation (fun validate Jwt)
        //JWT role check (fun getDetailsJwt)
        val name = "pietro"             //this field should be returned by the getDetailsJwt fun

        println(principal.name);

        //if (!validateJwt()) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val res : UserInfoDTO = travelerService.getUserDet(name)
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
        val name = "pietro"             //this field should be returned by the getDetailsJwt fun
        //if (!validateJwt()) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        if (br.hasErrors()) return ResponseEntity(HttpStatus.BAD_REQUEST)
        val ob = jacksonObjectMapper()
        val newInfo: UserInfoDTO = ob.readValue(body, UserInfoDTO::class.java)
        travelerService.updateUserDet(name, newInfo)
        return ResponseEntity("User Updated", HttpStatus.OK)
    }

    @GetMapping("/my/tickets")
    fun getTickets(
        @RequestHeader("Authorization")
        header: String
    ) : ResponseEntity<List<TicketDTO>> {
        //JWT validation (fun validate Jwt)
        //JWT role check (fun getDetailsJwt)
        val name = "pietro"             //this field should be returned by the getDetailsJwt fun
        //if (!validateJwt()) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val res : List<TicketDTO> = travelerService.getUserTickets(name)
        return ResponseEntity(res, HttpStatus.OK)
    }

    private data class AddingTicketReq(val cmd: String, val quantity: Int, val zones: String)

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
        val name = "pietro"             //this field should be returned by the getDetailsJwt fun
        val ob = jacksonObjectMapper()
        val req = ob.readValue(body, AddingTicketReq::class.java)
        //if (!validateJwt()) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        if (req.cmd != "buy_tickets"|| br.hasErrors()) return ResponseEntity(HttpStatus.BAD_REQUEST)
        val res = travelerService.createUserTickets(name, req.quantity, req.zones)
        return ResponseEntity(res, HttpStatus.CREATED)
    }
*/
    @GetMapping("/admin/travelers")
    fun getAllUsers(
        @RequestHeader("Authorization")
        header: String
    ) : ResponseEntity<Any> {
        //JWT validation (fun validate Jwt)
        //JWT role check (fun getDetailsJwt)
        val role = "admin"
        //if (!validateJwt()) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        if (role != "admin") return ResponseEntity("Your are not an admin!", HttpStatus.UNAUTHORIZED)
        val res = travelerService.getTravelers()
        return ResponseEntity(res, HttpStatus.OK)
    }

    @GetMapping("/admin/{userID}/profile")
    fun getTravelerById(
        @RequestHeader("Authorization")
        header: String,
        @PathVariable userID: Long
    ) : ResponseEntity<Any> {
        //JWT validation (fun validate Jwt)
        //JWT role check (fun getDetailsJwt)
        val role = "admin"
        //if (!validateJwt()) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        if (role != "admin") return ResponseEntity("Your are not an admin!", HttpStatus.UNAUTHORIZED)
        val res = travelerService.getUserDetById(userID)
        return ResponseEntity(res, HttpStatus.OK)
    }

    @GetMapping("/admin/{userID}/tickets")
    fun getTravelerTicketsByUserId(
        @RequestHeader("Authorization")
        header: String,
        @PathVariable userID: Long
    ) : ResponseEntity<Any> {
        //JWT validation (fun validate Jwt)
        //JWT role check (fun getDetailsJwt)
        val role = "admin"
        //if (!validateJwt()) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        if (role != "admin") return ResponseEntity("Your are not an admin!", HttpStatus.UNAUTHORIZED)
        val res = travelerService.getTicketsByUserId(userID)
        return if (res.isEmpty()) ResponseEntity("No tickets for the specified user", HttpStatus.OK)
        else ResponseEntity(res, HttpStatus.OK)
    }
}
