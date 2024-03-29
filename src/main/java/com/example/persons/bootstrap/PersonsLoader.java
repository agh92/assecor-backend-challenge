package com.example.persons.bootstrap;

import com.example.persons.domain.Person;
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
      var inputStream = personFileLoader.loadFile();
      var persons = personParser.parse(inputStream);
      personRepository.saveAll(persons);
    } catch (Exception ignored) {
      // ignore exception because we can still work with the repository
    }
  }
}
