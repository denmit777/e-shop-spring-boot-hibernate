package com.training.eshop.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.eshop.model.Order;
import com.training.eshop.service.OrderService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void resetDb() {
        orderService.deleteAll();
    }

    @Test
    public void givenOrder_whenAdd_thenStatus201andOrderReturned() throws Exception {
        Order order = new Order(1L, BigDecimal.valueOf(315));

        mockMvc.perform(
                post("/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.userId").value("1"))
                .andExpect(jsonPath("$.totalPrice").value("315"));
    }

    @Test
    public void givenId_whenGetExistingOrder_thenStatus200andOrderReturned() throws Exception {
        long id = createTestOrder(1L, BigDecimal.valueOf(315)).getId();

        mockMvc.perform(
                get("/orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.userId").value("1"))
                .andExpect(jsonPath("$.totalPrice").value("315.0"));
    }

    @Test
    public void giveOrder_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        long id = createTestOrder(1L, BigDecimal.valueOf(315)).getId();

        mockMvc.perform(
                put("/orders/{id}", id)
                        .content(objectMapper.writeValueAsString(new Order(2L, BigDecimal.valueOf(35.5))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.userId").value("2"))
                .andExpect(jsonPath("$.totalPrice").value("35.5"));
    }

    @Test
    public void givenOrder_whenDeleteOrder_thenStatus200() throws Exception {
        Order order = createTestOrder(1L, BigDecimal.valueOf(315));

        mockMvc.perform(
                delete("/orders/{id}", order.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOrders_whenGetOrders_thenStatus200() throws Exception {
        List<Order> orders = orderService.getAll();

        mockMvc.perform(
                get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(orders)));
    }

    private Order createTestOrder(Long userId, BigDecimal totalPrice) {
        Order order = new Order(userId, totalPrice);

        return orderService.create(order);
    }
}