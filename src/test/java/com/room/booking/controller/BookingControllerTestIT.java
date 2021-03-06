package com.room.booking.controller;

import com.room.booking.BookingApplication;
import com.room.booking.repository.BookingRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BookingApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class BookingControllerTestIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookingRepository repository;

    @Test
    public void shouldFetchBookingsAvailableRoomsBetweenDateRange() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/bookings/rooms?start=2019-02-02&end=2019-02-10")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].roomId", Matchers.is(1)))
                .andExpect(jsonPath("$[1].roomId", Matchers.is(2)));
    }

    @Test
    public void shouldAllowToBookingAvailableRoom() throws Exception {
        final String json = "{" +
                "\"customerName\":\"jas\"," +
                "\"customerSurname\":\"kowalski\"," +
                "\"customerEmail\":\"jas.kowlaski@gmail.com\"," +
                "\"dateFrom\":\"2019-12-29\"," +
                "\"dateTo\":\"2019-12-30\"" +
                "}";
        mvc.perform(post("/bookings/1/book")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("bookingId", Matchers.is(6)))
                .andExpect(jsonPath("room.roomId", Matchers.is(1)))
                .andExpect(jsonPath("bookingStatus", Matchers.is("CREATED")));
    }

    @Test
    public void shouldNotAllowToBookingUnavailableRoom() throws Exception {
        final String json = "{" +
                "\"customerName\":\"jas\"," +
                "\"customerSurname\":\"kowalski\"," +
                "\"customerEmail\":\"jas.kowlaski@gmail.com\"," +
                "\"dateFrom\":\"2019-01-01\"," +
                "\"dateTo\":\"2019-02-01\"" +
                "}";
        mvc.perform(post("/bookings/1/book")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", Matchers.is("Selected room is not available for date range 2019-01-01 and 2019-02-01")));
    }

    @Test
    public void shouldAllowToCancelBooking() throws Exception {
        final String actualStatus = repository.findById(3L).map(b -> b.getBookingStatus().toString()).orElse("");

        final String requestJson = "{" +
                "\"customerName\":\"jaś\"," +
                "\"customerSurname\":\"kowalski\"," +
                "\"customerEmail\":\"test2@test.pl\"" +
                "}";

        assertEquals("CREATED", actualStatus);

        mvc.perform(post("/bookings/3/cancel")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("bookingId", Matchers.is(3)))
                .andExpect(jsonPath("room.roomId", Matchers.is(2)))
                .andExpect(jsonPath("bookingStatus", Matchers.is("CANCELLED")));
    }

    @Test
    public void shouldNotAllowToCancelNotExistingBooking() throws Exception {
        final String requestJson = "{" +
                "\"customerName\":\"jaś\"," +
                "\"customerSurname\":\"kowalski\"," +
                "\"customerEmail\":\"test2@test.pl\"" +
                "}";

        mvc.perform(post("/bookings/999/cancel")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", Matchers.is("Booking doesn't exists")));
    }


    @Test
    public void shouldNotAllowToCancelOtherCustomerBooking() throws Exception {
        final String requestJson = "{" +
                "\"customerName\":\"jaś\"," +
                "\"customerSurname\":\"kowalski\"," +
                "\"customerEmail\":\"test2@test.pl\"" +
                "}";

        mvc.perform(post("/bookings/1/cancel")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", Matchers.is("Can't cancel others booking")));
    }


    @Test
    public void shouldNotAllowToBookingRoomWithoutCustomerEmail() throws Exception {
        final String json = "{" +
                "\"customerName\":\"jas\"," +
                "\"customerSurname\":\"kowalski\"," +
                "\"dateFrom\":\"2019-10-29\"," +
                "\"dateTo\":\"2019-10-30\"" +
                "}";
        mvc.perform(post("/bookings/1/book")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", Matchers.is("emailAddress - must not be blank")));
    }
}