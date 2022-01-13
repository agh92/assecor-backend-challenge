package com.example.persons.repository;

import com.example.persons.exception.NotImplementedException;
import com.example.persons.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PersonsRepository implements CrudRepository<Person, Long> {

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
        throw new NotImplementedException("findById");
    }

    @Override
    public boolean existsById(Long aLong) {
        throw new NotImplementedException("existsById");
    }

    @Override
    public Iterable<Person> findAll() {
        throw new NotImplementedException("findAll");
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
