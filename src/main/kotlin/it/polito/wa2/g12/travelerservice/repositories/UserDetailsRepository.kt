package it.polito.wa2.g12.travelerservice.repositories

import it.polito.wa2.g12.travelerservice.entities.UserDetails
import org.springframework.data.repository.CrudRepository

interface UserDetailsRepository : CrudRepository<UserDetails, Long> {
    fun existsByName(name: String): Boolean
    fun existsByPhoneNumber(phoneNumber: Int): Boolean
}