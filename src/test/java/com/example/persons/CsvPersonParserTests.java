package com.example.persons;

import com.example.persons.model.Color;
import com.example.persons.model.Person;
import com.example.persons.personfile.CsvPersonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvPersonParserTests {
    CsvPersonParser parser;

    @BeforeEach
    void beforeEach() {
        parser = new CsvPersonParser();
    }

    private static Stream<Arguments> parseReturnsCorrectNumberOfPersonsArguments() {
        return Stream.of(
                Arguments.of("Müller, Hans, 67742 Lauterecken, 1\nPetersen, Peter, 18439 Stralsund, 2", 2),
                Arguments.of("12313 Wasweißich, 1 \nGerber, Gerda, 76535 Woanders, 3", 1),
                Arguments.of("Gerber, Gerda, 76535 Woanders, 8 \nKlaussen, Klaus, 43246 Hierach, 2", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("parseReturnsCorrectNumberOfPersonsArguments")
    void parseReturnsCorrectNumberOfPersons(String csvString, Integer expectedNumberOfPersons) throws IOException {
        InputStream stream = new ByteArrayInputStream(csvString.getBytes(StandardCharsets.UTF_8));

        List<Person> persons = parser.parse(stream);

        assertEquals(expectedNumberOfPersons, persons.size());
    }

    @Test
    void parseReturnsCorrectPersonWithCorrectLastname() throws IOException {
        String expectedLastName = "Müller";
        String csvString = expectedLastName + ", Hans, 67742 Lauterecken, 1\nPetersen, Peter, 18439 Stralsund, 2";
        InputStream stream = new ByteArrayInputStream(csvString.getBytes(StandardCharsets.UTF_8));

        Person person = parser.parse(stream).stream().findFirst().get();

        assertEquals(expectedLastName, person.getLastName());
    }


    @Test
    void parseReturnsCorrectPersonWithCorrectName() throws IOException {
        String expectedName = "Hans";
        String csvString = "Müller, " + expectedName + ", 67742 Lauterecken, 1\nPetersen, Peter, 18439 Stralsund, 2";
        InputStream stream = new ByteArrayInputStream(csvString.getBytes(StandardCharsets.UTF_8));

        Person person = parser.parse(stream).stream().findFirst().get();

        assertEquals(expectedName, person.getName());
    }


    @Test
    void parseReturnsCorrectPersonWithCorrectAddress() throws IOException {
        String expectedAddress = "67742 Lauterecken";
        String csvString = "Müller, Hans, " + expectedAddress + ", 1\nPetersen, Peter, 18439 Stralsund, 2";
        InputStream stream = new ByteArrayInputStream(csvString.getBytes(StandardCharsets.UTF_8));

        Person person = parser.parse(stream).stream().findFirst().get();

        assertEquals(expectedAddress, person.getAddress());
    }

    @Test
    void parseReturnsCorrectPersonWithCorrectColor() throws IOException {
        Color expectedColor = Color.BLUE;
        String csvString = "Müller, Hans, 67742 Lauterecken, 1\nPetersen, Peter, 18439 Stralsund, " + expectedColor.ordinal();
        InputStream stream = new ByteArrayInputStream(csvString.getBytes(StandardCharsets.UTF_8));

        Person person = parser.parse(stream).stream().findFirst().get();

        assertEquals(expectedColor, person.getColor());
    }

}
