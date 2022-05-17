package it.polito.wa2.g12.travelerservice.repositories

import it.polito.wa2.g12.travelerservice.dto.UserDetailsDto
import it.polito.wa2.g12.travelerservice.entities.UserDetails
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.math.BigInteger
import java.util.*
import javax.persistence.LockModeType
import javax.persistence.QueryHint

interface UserDetailsRepository : CrudRepository<UserDetails, Long> {
    fun findByName(name: String): Optional<UserDetails>

    @Query("SELECT DISTINCT u.name FROM UserDetails u")
    fun findAllTravelers(): List<String>
}