package com.sergdalm.integration.controller;

import com.sergdalm.entity.UserInfo_;
import com.sergdalm.entity.User_;
import com.sergdalm.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RequiredArgsConstructor
@AutoConfigureMockMvc
public class UserControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(6)));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/specialists/1"))
                .andExpectAll(status().is2xxSuccessful())
                .andExpectAll(view().name("specialists/specialist"))
                .andExpectAll(model().attributeExists("specialist"));
    }

    @Test
    void findByIdNotExistedUser() throws Exception {
        mockMvc.perform(get("/users/" + Integer.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser() throws Exception {
        mockMvc.perform(post("/users")
                        .param(User_.EMAIL, "test@gmail.com")
                        .param(User_.FIRST_NAME, "Test")
                        .param(User_.LAST_NAME, "TestTest")
                        .param(User_.ROLE, "ADMIN")
                        .param("rowPassword", "1234")
                        .param(UserInfo_.BIRTHDAY, "1999-05-06")
                        .param(UserInfo_.GENDER, "MALE"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        // здесь используется Ant паттерн
                        redirectedUrlPattern("/users/login")
                );
    }
}
