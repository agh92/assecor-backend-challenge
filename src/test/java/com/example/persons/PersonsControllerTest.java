package com.example.persons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.persons.controller.PersonsController;
import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import com.example.persons.service.PersonsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@SpringBootTest
@ContextConfiguration(classes = {PersonsController.class})
@Import(PersonsControllerTest.Config.class)
public class PersonsControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    PersonsService personsService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PersonsController personsController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personsController).build();
    }

    @Test
    void getAllPersonsResponseIsOk() throws Exception {
        mockMvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllPersonsReturnsAllPersons() throws Exception {
        Integer expectedNumberOfPersons = 5;
        given(personsService.findAll()).willReturn(buildPerson(expectedNumberOfPersons));

        MvcResult result = mockMvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON)).andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        List<PersonDto> personDtos = MAPPER.readValue(contentAsString, new TypeReference<>() {
        });

        assertEquals(personDtos.size(), expectedNumberOfPersons);
    }

    @Test
    void getPersonResponseIsOk() throws Exception {
        given(personsService.findById(anyLong())).willReturn(Optional.of(buildPerson()));

        mockMvc.perform(get("/persons/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getPersonReturnsTheCorrectPerson() throws Exception {
        Person person = buildPerson();
        Long expectedId = person.getId();
        given(personsService.findById(anyLong())).willReturn(Optional.of(person));

        MvcResult result = mockMvc.perform(get("/persons/" + expectedId).accept(MediaType.APPLICATION_JSON)).andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        PersonDto personDto = MAPPER.readValue(contentAsString, PersonDto.class);

        assertEquals(personDto.getId(), expectedId);
    }

    @Test
    void getPersonReturnsNotFound() throws Exception {
        given(personsService.findById(any())).willReturn(Optional.empty());

        mockMvc.perform(get("/persons/999999").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void getPersonReturnsBadRequestForWrongParamType() throws Exception {
        mockMvc.perform(get("/persons/foo").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void getPersonsByColorResponseIsOk() throws Exception {
        given(personsService.findByColor(any())).willReturn(buildPerson(2));

        Color color = Color.GREEN;
        mockMvc.perform(get("/persons/color/" + color).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getMatchingPersonsReturnsCorrectNumberOfPersons() throws Exception {
        Integer expectedNumberOfPersons = 5;
        given(personsService.findByColor(any())).willReturn(buildPerson(expectedNumberOfPersons));

        Color color = Color.GREEN;
        MvcResult result = mockMvc.perform(get("/persons/color/" + color).accept(MediaType.APPLICATION_JSON)).andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        List<PersonDto> personDtos = MAPPER.readValue(contentAsString, new TypeReference<>() {
        });

        assertEquals(personDtos.size(), expectedNumberOfPersons);
    }

    @Test
    void getMatchingPersonsReturnsBadRequestForInvalidColor() throws Exception {
        mockMvc.perform(get("/persons/color/foo").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void getMatchingPersonsReturnsBadRequestForWrongParamType() throws Exception {
        mockMvc.perform(get("/persons/color/999").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void createPersonRespondsCreatedStatus() throws Exception {
        PersonDto personDto = buildPersonDtoWithoutId();
        String jsonPerson = MAPPER.writeValueAsString(personDto);

        mockMvc.perform(post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson)
        ).andExpect(status().isCreated());
    }

    @Test
    void createPersonRespondsBadRequestStatusIfIdIsPresent() throws Exception {
        PersonDto personDto = buildPersonDto();
        String jsonPerson = MAPPER.writeValueAsString(personDto);

        mockMvc.perform(post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson)
        ).andExpect(status().isBadRequest());
    }

    static Person buildPerson() {
        return Person.builder().id(1L)
                .name("john")
                .lastName("doe")
                .address("12345 somewhere")
                .color(Color.BLUE)
                .build();
    }

    static PersonDto buildPersonDto() {
        return getJohnDoeBuilder().id(1L).build();
    }

    static PersonDto buildPersonDtoWithoutId() {
        return getJohnDoeBuilder().build();
    }

    static PersonDto.PersonDtoBuilder getJohnDoeBuilder() {
        return PersonDto.builder()
                .name("john")
                .lastName("doe")
                .zipCode(12345)
                .city(" somewhere")
                .color("blue");
    }

    static List<Person> buildPerson(Integer number) {
        List<Person> persons = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            persons.add(buildPerson());
        }
        return persons;
    }

    @TestConfiguration
    protected static class Config {
        @Bean
        PersonsService personsService() {
            return Mockito.mock(PersonsService.class);
        }

        @Bean
        ModelMapper modelMapper() {
            ModelMapper mock = Mockito.mock(ModelMapper.class);

            given(mock.map(any(Person.class), any())).willReturn(buildPersonDto());

            return mock;
        }
    }
}
