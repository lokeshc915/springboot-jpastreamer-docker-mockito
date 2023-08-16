package com.springboot.jpastreamer.service;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.springboot.jpastreamer.entity.Person;
import com.springboot.jpastreamer.entity.Person$;
import com.springboot.jpastreamer.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final JPAStreamer jpaStreamer;
    private final PersonRepository personRepository;

    public List<Person> saveAllForJpaRepository(List<Person> PersonList) {
        return personRepository.saveAll(PersonList);
    }


    public List<Person> findAllForJpaStreamer() {
        return jpaStreamer.stream(Person.class).
                sorted(Person$.id).
                collect(Collectors.toList());
    }

    public Person findByIdForJpaStreamer(Long id) {
        return jpaStreamer.stream(Person.class).
                filter(Person$.id.equal(id)).
                findFirst().map(u -> u).
                orElse(null);
    }

    public List<Person> findByAgeForJpaStreamer(Integer age) {
        return jpaStreamer.stream(Person.class).
                filter(Person$.age.equal(age)).
                collect(Collectors.toList());
    }

    public List<Person> findByFirstCharacterOfFirstNameForJpaStreamer(String character) {
        return jpaStreamer.stream(Person.class).
                filter(Person$.firstName.startsWith(character)).
                collect(Collectors.toList());
    }


    public List<Person> findByLessThanAgeForJpaStreamer(Integer age) {
        return jpaStreamer.stream(Person.class).
                filter(Person$.age.lessThan(age)).
                collect(Collectors.toList());
    }


    public List<Person> findByFirstCharacterOfFirstNameAndAgeForJpaStreamer(String character, Integer age) {
        return jpaStreamer.stream(Person.class).
                filter(Person$.firstName.startsWith(character).and(Person$.age.equal(age))).
                collect(Collectors.toList());
    }

    public List<Person> findByAgeRangeForJpaStreamer(Integer age1, Integer age2) {
        return jpaStreamer.stream(Person.class).
                filter(Person$.age.between(age1, age2)).
                collect(Collectors.toList());
    }

    public Person findMaximumAgeForJpaStreamer() {
        return jpaStreamer.stream(Person.class).
                max(Comparator.comparing(Person::getAge)).
                get();
    }

    public Person findMinimumAgeForJpaStreamer() {
        return jpaStreamer.stream(Person.class).
                min(Comparator.comparing(Person::getAge)).
                get();
    }

    public List<Person> findByBirthdayRangeForJpaStreamer(LocalDate startDate, LocalDate endDate) {
        return jpaStreamer.stream(Person.class)
                .filter(Person$.birthday.between(startDate, endDate))
                .collect(Collectors.toList());
    }


}