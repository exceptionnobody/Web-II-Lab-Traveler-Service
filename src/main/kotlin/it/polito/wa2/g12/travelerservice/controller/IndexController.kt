package it.polito.wa2.g12.travelerservice.controller

import it.polito.wa2.g12.travelerservice.dto.UserInfoDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import it.polito.wa2.g12.travelerservice.service.implementation.TravelerServiceImpl

@RestController
@RequestMapping("/my")
class IndexController(val travelerService: TravelerServiceImpl) {

    @GetMapping(value = ["/profile"])
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
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
        val res : UserInfoDTO = travelerService.getUserDet(principal.name)
        return ResponseEntity(res, HttpStatus.OK)
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