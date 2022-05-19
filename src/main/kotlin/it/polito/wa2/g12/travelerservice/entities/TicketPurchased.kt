package it.polito.wa2.g12.travelerservice.entities

import it.polito.wa2.g12.travelerservice.dto.TicketDTO
import java.time.LocalDateTime
import java.util.Date
import javax.persistence.*

@Entity
class TicketPurchased(
    @Column(nullable = false)
    var zone: String,
    @ManyToOne
    var userDet: UserDetails
): EntityBase<Long>() {
    @Column (nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    var issuedAt: Date = java.sql.Timestamp.valueOf(LocalDateTime.now())
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    var deadline: Date = java.sql.Timestamp.valueOf(LocalDateTime.now().plusHours(1))
}

fun TicketPurchased.toDTO(ticketId: Long?,jws:String) = TicketDTO(ticketId, zone, issuedAt.toString(), deadline.toString(),jws)