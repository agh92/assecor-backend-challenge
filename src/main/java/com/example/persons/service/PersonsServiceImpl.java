package com.example.persons.service;

import com.example.persons.exception.PersonNotFoundException;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import com.example.persons.personfile.PersonFileLoader;
import com.example.persons.personfile.PersonParser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class PersonsServiceImpl implements PersonsService, InitializingBean {

    private final CrudRepository<Person, Long> personRepository;
    private final PersonFileLoader personFileLoader;
    private final PersonParser personParser;

    @Override
    public void afterPropertiesSet() {
        try {
            InputStream inputStream = personFileLoader.loadFile();
            List<Person> persons = personParser.parse(inputStream);
            personRepository.saveAll(persons);
        } catch (Exception ignored) {
            // ignore exception because we can still add persons
        }
    }

    public List<Person> findAll() {
        Iterable<Person> persons = personRepository.findAll();

        return StreamSupport
                .stream(persons.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Person findById(Long id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            return person.get();
        }

        throw new PersonNotFoundException(id);
    }

    public List<Person> findByColor(Color color) {
        Iterable<Person> persons = personRepository.findAll();

        return StreamSupport
                .stream(persons.spliterator(), false)
                .filter(person -> person.getColor() == color)
                .collect(Collectors.toList());
    }

    public void createPerson(Person person) {
        this.personRepository.save(person);
    }
}
