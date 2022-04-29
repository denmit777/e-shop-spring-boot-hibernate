package com.training.eshop.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.eshop.model.User;
import com.training.eshop.service.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void resetDb() {
        userService.deleteAll();
    }

    @Test
    public void givenUser_whenAdd_thenStatus201andUserReturned() throws Exception {
        User user = new User("Nick", "Nick@mail.ru", "1567", "user");

        mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.login").value("Nick"))
                .andExpect(jsonPath("$.email").value("Nick@mail.ru"))
                .andExpect(jsonPath("$.password").value("1567"))
                .andExpect(jsonPath("$.userRole").value("user"));
    }

    @Test
    public void givenId_whenGetExistingUser_thenStatus200andUserReturned() throws Exception {
        long id = createTestUser("Nick", "Nick@mail.ru", "1567", "user").getId();

        mockMvc.perform(
                get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.login").value("Nick"))
                .andExpect(jsonPath("$.email").value("Nick@mail.ru"))
                .andExpect(jsonPath("$.password").value("1567"))
                .andExpect(jsonPath("$.userRole").value("user"));
    }

    @Test
    public void giveUser_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        long id = createTestUser("Nick", "Nick@mail.ru", "1567", "user").getId();

        mockMvc.perform(
                put("/users/{id}", id)
                        .content(objectMapper.writeValueAsString(new User("Kate", "Kate@mail.ru", "7432", "admin")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.login").value("Kate"))
                .andExpect(jsonPath("$.email").value("Kate@mail.ru"))
                .andExpect(jsonPath("$.password").value("7432"))
                .andExpect(jsonPath("$.userRole").value("admin"));
    }

    @Test
    public void givenUser_whenDeleteUser_thenStatus200() throws Exception {
        User user = createTestUser("Nick", "Nick@mail.ru", "1567", "user");

        mockMvc.perform(
                delete("/users/{id}", user.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void givenUsers_whenGetUsers_thenStatus200() throws Exception {
        List<User> users = userService.getAll();

        mockMvc.perform(
                get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));
    }

    private User createTestUser(String login, String email, String password, String userRole) {
        User user = new User(login, email, password, userRole);

        return userService.save(user);
    }
}