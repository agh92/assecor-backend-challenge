package com.example.persons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.persons.controller.PersonsController;
import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

@WebMvcTest(PersonsController.class)
public class PersonsControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllPersonsResponseIsOk() throws Exception {
        mockMvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllPersonsReturnsAllPersons() throws Exception {
        MvcResult result = mockMvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<PersonDto> personDtos = MAPPER.readValue(contentAsString, new TypeReference<>() {
        });
        assertEquals(personDtos.size(), 11);
    }

    @Test
    void getPersonResponseIsOk() throws Exception {
        mockMvc.perform(get("/persons/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getPersonReturnsTheCorrectPerson() throws Exception {
        Long expectedId = 1L;
        MvcResult result = mockMvc.perform(get("/persons/" + expectedId).accept(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        PersonDto personDto = MAPPER.readValue(contentAsString, PersonDto.class);
        assertEquals(personDto.getId(), expectedId);
    }

    @Test
    void getPersonReturnsNotFound() throws Exception {
        mockMvc.perform(get("/persons/999999").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void getPersonReturnsBadRequestForWrongParamType() throws Exception {
        mockMvc.perform(get("/persons/foo").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void getPersonsWithSameFavoriteColorResponseIsOk() throws Exception {
        mockMvc.perform(get("/persons/color/2").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getPersonsWithSameFavoriteColorReturnsPersonsWithSameColor() throws Exception {
        Color color = Color.GREEN;
        MvcResult result = mockMvc.perform(get("/persons/color/" + color).accept(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<PersonDto> personDtos = MAPPER.readValue(contentAsString, new TypeReference<>() {
        });
        assertEquals(personDtos.size(), 3);
    }

    @Test
    void getPersonsWithSameFavoriteColorReturnsBadRequestForInvalidColor() throws Exception {
        mockMvc.perform(get("/persons/color/999").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void getPersonsWithSameFavoriteColorReturnsBadRequestForWrongParamType() throws Exception {
        mockMvc.perform(get("/persons/color/foo").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

}
