package it.polito.wa2.g12.travelerservice

import it.polito.wa2.g12.travelerservice.entities.UserDetails
import it.polito.wa2.g12.travelerservice.repositories.TicketPurchasedRepository
import it.polito.wa2.g12.travelerservice.repositories.UserDetailsRepository
import it.polito.wa2.g12.travelerservice.service.implementation.TravelerServiceImpl
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.*
import org.mockito.Mockito.times
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class TravelerServiceImplTests {

    @InjectMocks
    lateinit var travelerServiceImpl: TravelerServiceImpl
    @Mock
    lateinit var ticketsRepo: TicketPurchasedRepository
    @Mock
    lateinit var userDetRepo: UserDetailsRepository

    @Test
    fun getUserDetTest() {
        Mockito.`when`(userDetRepo.findByName("test")).thenReturn(
            Optional.of(UserDetails("test","test", Date(0),"0123456789"))
        )
        val notNullUserInfoDto = travelerServiceImpl.getUserDet("test")
        Mockito.verify(userDetRepo, times(2)).findByName("test")
        Mockito.clearInvocations(userDetRepo)
        assertNotNull(notNullUserInfoDto)
        assert(notNullUserInfoDto!!.name == "test")

        Mockito.`when`(userDetRepo.findByName("test")).thenReturn(Optional.empty())
        val nullUserInfoDto = travelerServiceImpl.getUserDet("test")
        Mockito.verify(userDetRepo, times(1)).findByName("test")
        Mockito.clearInvocations(userDetRepo)
        assertNull(nullUserInfoDto)
    }

    @Test
    fun getUserDetByIdTest() {
        Mockito.`when`(userDetRepo.findById(1)).thenReturn(
            Optional.of(UserDetails("test","test", Date(0),"0123456789"))
        )
        val notNullUserInfoDto = travelerServiceImpl.getUserDetById(1)
        Mockito.verify(userDetRepo).findById(1)
        Mockito.clearInvocations(userDetRepo)
        assertNotNull(notNullUserInfoDto)
        assert(notNullUserInfoDto!!.name == "test")

        Mockito.`when`(userDetRepo.findById(1)).thenReturn(Optional.empty())
        val nullUserInfoDto = travelerServiceImpl.getUserDetById(1)
        Mockito.verify(userDetRepo).findById(1)
        Mockito.clearInvocations(userDetRepo)
        assertNull(nullUserInfoDto)
    }

    @Test
    fun getTravelersTest() {
        Mockito.`when`(userDetRepo.findAllTravelers()).thenReturn(
            listOf("test1","test2","test3")
        )
        val travelers = travelerServiceImpl.getTravelers()
        for (i in 0..2) {
            assert(travelers[i] == "test"+(i+1))
        }
    }

    /*
    @Test
    fun getUserTicketsTest() {
        val userDetails = UserDetails("test","test", Date(0),"0123456789")
        Mockito.`when`(userDetRepo.findByName("test")).thenReturn(
            Optional.of(userDetails)
        )
        userDetails.setId(1L)
        //ReflectionTestUtils.setField(secretKey, "ticketKey", "N3cheiVDJkYpSkBOY1JmVWpYbjJyNXU4eC9BP0QoRy0=");
        Mockito.`when`(ticketsRepo.findAllByUserDet(1L)).thenReturn(
            listOf("1,2022-05-20 03:55:21.000000,2022-05-20 03:55:21.000000,A")
        )
        val ticketList = travelerServiceImpl.getUserTickets("test")
        Mockito.clearInvocations(userDetRepo)
        Mockito.clearInvocations(ticketsRepo)
        assertNotNull(ticketList)
    }
    @SpyBean
    lateinit var secretKey: SecretKey
    @Before
    fun setUp() {
        //MockitoAnnotations.initMocks()
        //secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("N3cheiVDJkYpSkBOY1JmVWpYbjJyNXU4eC9BP0QoRy0="))
        //ReflectionTestUtils.setField(travelerServiceImpl, "secretKey", Keys.hmacShaKeyFor(Decoders.BASE64.decode("N3cheiVDJkYpSkBOY1JmVWpYbjJyNXU4eC9BP0QoRy0=")))
    }
    */
}