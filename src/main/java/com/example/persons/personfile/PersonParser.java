package com.example.persons.personfile;

import com.example.persons.model.Person;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Parses the data inside the loaded file
 * @see {@link PersonFileLoader}
 */
public interface PersonParser {
    List<Person> parse(InputStream inputStream) throws IOException;
}
