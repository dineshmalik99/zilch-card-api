package com.zilch.cards.controller;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ZilchCardControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnSuccessResponse() throws Exception {

    this.mockMvc.perform(post("/zilch/cards").contentType(MediaType.APPLICATION_JSON)
            .content(getAddCardDetailsBodyString())).andExpect(status().isOk())
        .andExpect(content().string(containsString("9113")));

  }

  @Test
  public void shouldReturnErrorResponse() throws Exception {

    this.mockMvc.perform(post("/zilch/cards").contentType(MediaType.APPLICATION_JSON)
            .content(getAddCardDetailsBodyStringWithInvalidCard())).andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Invalid CardNo")));

  }

  @Test
  public void shouldReturnNotFound() throws Exception {

    this.mockMvc.perform(get("/zilch/cards/card").contentType(MediaType.APPLICATION_JSON)
            .header("userId", "user1").header("last4Digit", "1234")).andExpect(status().isNotFound())
        .andExpect(content().string(containsString("No Record found")));

  }


  private String getAddCardDetailsBodyString() throws JsonProcessingException {
    return "{\n"
        + "  \"userId\": \"user123\",\n"
        + "  \"userFirstName\": \"Dinesh\",\n"
        + "  \"userLastName\": \"Malik\",\n"
        + "  \"cardNo\": \"4417123456789113\",\n"
        + "  \"expiryDate\": \"2024-02-02\",\n"
        + "  \"cvv\": \"123\"\n"
        + "}";
  }

  private String getAddCardDetailsBodyStringWithInvalidCard() throws JsonProcessingException {
    return "{\n"
        + "  \"userId\": \"user123\",\n"
        + "  \"userFirstName\": \"Dinesh\",\n"
        + "  \"userLastName\": \"Malik\",\n"
        + "  \"cardNo\": \"4417123456789119\",\n"
        + "  \"expiryDate\": \"2024-02-02\",\n"
        + "  \"cvv\": \"123\"\n"
        + "}";
  }


}
