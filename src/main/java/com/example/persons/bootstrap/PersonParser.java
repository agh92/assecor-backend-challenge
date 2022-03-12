package com.example.persons.bootstrap;

import com.example.persons.domain.Person;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Parses the data inside the loaded file
 *
 * @see {@link PersonFileLoader}
 */
public interface PersonParser {
  List<Person> parse(InputStream inputStream) throws IOException;
}
