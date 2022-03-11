package com.example.persons.bootstrap;

import com.example.persons.model.Person;
import java.io.InputStream;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PersonsLoader implements CommandLineRunner {

  private final CrudRepository<Person, Long> personRepository;

  private final PersonFileLoader personFileLoader;
  private final PersonParser personParser;

  @Override
  public void run(String... args) {
    this.loadPersons();
  }

  public void loadPersons() {
    try {
      InputStream inputStream = personFileLoader.loadFile();
      List<Person> persons = personParser.parse(inputStream);
      personRepository.saveAll(persons);
    } catch (Exception ignored) {
      // ignore exception because we can still work with the repository
    }
  }
}
