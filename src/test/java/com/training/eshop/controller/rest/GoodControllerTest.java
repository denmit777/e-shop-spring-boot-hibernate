package com.training.eshop.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.eshop.model.Good;
import com.training.eshop.service.GoodService;

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
public class GoodControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GoodService goodService;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void resetDb() {
        goodService.deleteAll();
    }

    @Test
    public void givenGood_whenAdd_thenStatus201andGoodReturned() throws Exception {
        Good good = new Good("Book", BigDecimal.valueOf(7.5));

        mockMvc.perform(
                post("/goods")
                        .content(objectMapper.writeValueAsString(good))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Book"))
                .andExpect(jsonPath("$.price").value("7.5"));
    }

    @Test
    public void givenId_whenGetExistingGood_thenStatus200andGoodReturned() throws Exception {
        long id = createTestGood("Book", BigDecimal.valueOf(7.5)).getId();

        mockMvc.perform(
                get("/goods/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Book"))
                .andExpect(jsonPath("$.price").value("7.5"));
    }

    @Test
    public void giveGood_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        long id = createTestGood("Book", BigDecimal.valueOf(7.5)).getId();

        mockMvc.perform(
                put("/goods/{id}", id)
                        .content(objectMapper.writeValueAsString(new Good("Beer", BigDecimal.valueOf(3.5))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Beer"))
                .andExpect(jsonPath("$.price").value("3.5"));
    }

    @Test
    public void givenGood_whenDeleteGood_thenStatus200() throws Exception {
        Good good = createTestGood("Book", BigDecimal.valueOf(7.5));

        mockMvc.perform(
                delete("/goods/{id}", good.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void givenGoods_whenGetGoods_thenStatus200() throws Exception {
        List<Good> goods = goodService.getAll();

        mockMvc.perform(
                get("/goods"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(goods)));
    }

    private Good createTestGood(String name, BigDecimal price) {
        Good good = new Good(name, price);

        return goodService.save(good);
    }
}