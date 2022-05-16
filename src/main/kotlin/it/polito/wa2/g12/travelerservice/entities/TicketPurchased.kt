package it.polito.wa2.g12.travelerservice.entities

import it.polito.wa2.g12.travelerservice.dto.TicketDTO
import it.polito.wa2.g12.travelerservice.dto.UserDetailsDto
import java.time.LocalDateTime
import java.util.Date
import javax.persistence.*

@Entity
class TicketPurchased(
    @Column(nullable = false)
    var zone: String,
    @Column (nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    var issuedAt: Date = java.sql.Timestamp.valueOf(LocalDateTime.now()),
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    var deadline: Date = java.sql.Timestamp.valueOf(LocalDateTime.now().plusHours(1)),
    @ManyToOne
    var userDet: UserDetails
): EntityBase<Long>()

fun TicketPurchased.toDTO() = TicketDTO(this.getId()!!, zone, issuedAt, deadline, userDet.getId()!!)