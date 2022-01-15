package com.example.persons.repository;

import com.example.persons.exception.NotImplementedException;
import com.example.persons.model.Person;
import com.example.persons.personfile.PersonParser;
import com.example.persons.personfile.PersonFileLoader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonsRepository implements CrudRepository<Person, Long> {

    private final List<Person> persons;

    public PersonsRepository(PersonFileLoader personFileLoader, PersonParser personParser) throws IOException {
        // load once at the start to avoid loading the data for every request
        // don't handle exceptions because it does not make sense to run the application without
        // successfully reading users from the file
        InputStream inputStream = personFileLoader.loadFile();
        this.persons = personParser.parse(inputStream);
    }

    @Override
    public <S extends Person> S save(S entity) {
        throw new NotImplementedException("save");
    }

    @Override
    public <S extends Person> Iterable<S> saveAll(Iterable<S> entities) {
        throw new NotImplementedException("saveAll");
    }

    @Override
    public Optional<Person> findById(Long aLong) {
        return persons.stream().filter(person -> person.getId().equals(aLong)).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        throw new NotImplementedException("existsById");
    }

    @Override
    public Iterable<Person> findAll() {
        return persons;
    }

    @Override
    public Iterable<Person> findAllById(Iterable<Long> longs) {
        throw new NotImplementedException("findAllById");
    }

    @Override
    public long count() {
        throw new NotImplementedException("count");
    }

    @Override
    public void deleteById(Long aLong) {
        throw new NotImplementedException("deleteById");
    }

    @Override
    public void delete(Person entity) {
        throw new NotImplementedException("delete");
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        throw new NotImplementedException("deleteAllById");
    }

    @Override
    public void deleteAll(Iterable<? extends Person> entities) {
        throw new NotImplementedException("deleteAll");
    }

    @Override
    public void deleteAll() {
        throw new NotImplementedException("deleteAll");
    }
}
