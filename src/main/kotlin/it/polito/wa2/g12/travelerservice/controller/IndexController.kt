package it.polito.wa2.g12.travelerservice.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class IndexController {

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