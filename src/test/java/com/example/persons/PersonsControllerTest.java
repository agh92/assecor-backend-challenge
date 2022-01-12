package com.example.persons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.persons.controller.PersonsController;
import com.example.persons.dto.PersonDto;
import com.example.persons.exception.PersonNotFoundException;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import com.example.persons.service.PersonsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;

@WebMvcTest(PersonsController.class)
public class PersonsControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @MockBean
    PersonsService personsService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllPersonsResponseIsOk() throws Exception {
        mockMvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllPersonsReturnsAllPersons() throws Exception {
        Integer expectedNumberOfPersons = 5;
        given(personsService.getAllPersons()).willReturn(buildPerson(expectedNumberOfPersons));

        MvcResult result = mockMvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON)).andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        List<PersonDto> personDtos = MAPPER.readValue(contentAsString, new TypeReference<>() {
        });

        assertEquals(personDtos.size(), expectedNumberOfPersons);
    }

    @Test
    void getPersonResponseIsOk() throws Exception {
        given(personsService.getPerson(anyInt())).willReturn(buildPerson());

        mockMvc.perform(get("/persons/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getPersonReturnsTheCorrectPerson() throws Exception {
        Person person = buildPerson();
        Long expectedId = person.getId();
        given(personsService.getPerson(anyInt())).willReturn(person);

        MvcResult result = mockMvc.perform(get("/persons/" + expectedId).accept(MediaType.APPLICATION_JSON)).andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        PersonDto personDto = MAPPER.readValue(contentAsString, PersonDto.class);

        assertEquals(personDto.getId(), expectedId);
    }

    @Test
    void getPersonReturnsNotFound() throws Exception {
        given(personsService.getPerson(any())).willThrow(new PersonNotFoundException());

        mockMvc.perform(get("/persons/999999").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void getPersonReturnsBadRequestForWrongParamType() throws Exception {
        mockMvc.perform(get("/persons/foo").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void getPersonsWithMatchingColorResponseIsOk() throws Exception {
        given(personsService.getMatchingPersons(any())).willReturn(buildPerson(2));

        Color color = Color.GREEN;
        mockMvc.perform(get("/persons/color/" + color).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getMatchingPersonsReturnsCorrectNumberOfPersons() throws Exception {
        Integer expectedNumberOfPersons = 5;
        given(personsService.getMatchingPersons(any())).willReturn(buildPerson(expectedNumberOfPersons));

        Color color = Color.GREEN;
        MvcResult result = mockMvc.perform(get("/persons/color/" + color).accept(MediaType.APPLICATION_JSON)).andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        List<PersonDto> personDtos = MAPPER.readValue(contentAsString, new TypeReference<>() {
        });

        assertEquals(personDtos.size(), expectedNumberOfPersons);
    }

    @Test
    void getMatchingPersonsReturnsBadRequestForInvalidColor() throws Exception {
        mockMvc.perform(get("/persons/color/999").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void getMatchingPersonsReturnsBadRequestForWrongParamType() throws Exception {
        mockMvc.perform(get("/persons/color/foo").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    static Person buildPerson() {
        return Person.builder().id(1L)
                .name("john")
                .lastName("doe")
                .address("12345 somewhere")
                .color(Color.BLUE)
                .build();
    }

    static List<Person> buildPerson(Integer number) {
        List<Person> persons = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            persons.add(buildPerson());
        }
        return persons;
    }
}
