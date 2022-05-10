package it.polito.wa2.g12.travelerservice.repositories

import it.polito.wa2.g12.travelerservice.entities.TicketPurchased
import org.springframework.data.repository.CrudRepository

interface TicketPurchasedRepository : CrudRepository<TicketPurchased, Long>