package it.polito.wa2.g12.travelerservice.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import it.polito.wa2.g12.travelerservice.dto.UserDetailsDto
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
    ) : ResponseEntity<String> {
        //JWT validation (fun validate Jwt)
        //JWT role check (fun getDetailsJwt)
        val userId = 1L             //this field should be returned by the getDetailsJwt fun
        //if (!validateJwt()) return ResponseEntity(HttpStatus.BAD_REQUEST)
        if (!travelerService.userExistsById(userId)) return ResponseEntity(HttpStatus.NOT_FOUND)
        val res : String = travelerService.myProfile(1L)
        return ResponseEntity(res, HttpStatus.ACCEPTED)
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
        if (/*!validateJwt() ||*/br.hasErrors()) return ResponseEntity(HttpStatus.BAD_REQUEST)
        if (!travelerService.userExistsById(userId)) return ResponseEntity(HttpStatus.NOT_FOUND)
        val ob = jacksonObjectMapper()
        val newInfo: UserDetailsDto = ob.readValue(body, UserDetailsDto::class.java)
        travelerService.updateMyProfile(userId, newInfo)
        return ResponseEntity("User Updated", HttpStatus.OK)
    }

    @GetMapping("/my/tickets")
    fun getTickets(
        @RequestHeader("Authorization")
        header: String
    ) : ResponseEntity<List<String>> {
        //JWT validation (fun validate Jwt)
        //JWT role check (fun getDetailsJwt)
        val userId = 1L             //this field should be returned by the getDetailsJwt fun
        //if (!validateJwt()) return ResponseEntity(HttpStatus.BAD_REQUEST)
        if (!travelerService.userExistsById(userId)) return ResponseEntity(HttpStatus.NOT_FOUND)
        val res : List<String> = travelerService.userTickets(userId)
        return ResponseEntity(res, HttpStatus.ACCEPTED)
    }
}