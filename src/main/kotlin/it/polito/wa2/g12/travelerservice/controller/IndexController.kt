package it.polito.wa2.g12.travelerservice.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import it.polito.wa2.g12.travelerservice.dto.TicketDTO
import it.polito.wa2.g12.travelerservice.dto.UserInfoDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import java.security.Principal
import it.polito.wa2.g12.travelerservice.service.implementation.TravelerServiceImpl
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/my")
class IndexController(val travelerService: TravelerServiceImpl) {

    @GetMapping(value = ["/profile"])
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    fun getUserDet(principal: Principal) : ResponseEntity<Any> {
        val res : UserInfoDTO = travelerService.getUserDet(principal.name)
        return ResponseEntity(res, HttpStatus.OK)
    }

    // To test this endpoint you can provide a JSON like this one:
    // {"name":"test", "address":"test", "date_of_birth":"2022-05-18", "number":123456789}
    // All the JSON fields are needed
    @PutMapping("/profile")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    fun updateUserDet(
        @RequestBody
        body: String,
        br: BindingResult,
        principal: Principal
    ) : ResponseEntity<String> {
        if (br.hasErrors())
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        val ob = jacksonObjectMapper()
        val newInfo: UserInfoDTO = ob.readValue(body, UserInfoDTO::class.java)
        travelerService.updateUserDet(principal.name, newInfo)
        return ResponseEntity("User updated.", HttpStatus.OK)
    }

    @GetMapping("/tickets")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    fun getTickets(principal: Principal) : ResponseEntity<List<TicketDTO>> {
        val res : List<TicketDTO> = travelerService.getUserTickets(principal.name)
        return ResponseEntity(res, HttpStatus.OK)
    }

    private data class AddingTicketReq(val cmd: String, val quantity: Int, val zones: String)

    @PostMapping("/tickets")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    fun postTickets(
        @RequestBody
        body: String,
        br: BindingResult,
        principal: Principal
    ) : ResponseEntity<List<TicketDTO>> {
        val ob = jacksonObjectMapper()
        val req = ob.readValue(body, AddingTicketReq::class.java)
        if (req.cmd != "buy_tickets"|| br.hasErrors())
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        val res = travelerService.createUserTickets(principal.name, req.quantity, req.zones)
        return ResponseEntity(res, HttpStatus.CREATED)
    }

    @GetMapping(value = ["", "/", "/test"])
    fun helloWorld() = "Test!"

    @GetMapping(value = ["/required"])
    fun helloRequiredWorld(@RequestParam(value = "msg", required = true) msg: String) = "Echo \"$msg\"!"

    @GetMapping(value = ["/customer"])
    @PreAuthorize("hasAuthority('CUSTOMER')")
    fun helloRestrictedWorld() = "Hi CUSTOMER!"

    @GetMapping(value = ["/foradmin"])
    @PreAuthorize("hasAuthority('ADMIN')")
    fun helloAdminWorld() = "Admin!"
}