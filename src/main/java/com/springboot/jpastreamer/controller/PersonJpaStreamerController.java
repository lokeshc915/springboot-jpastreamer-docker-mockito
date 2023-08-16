package com.springboot.jpastreamer.controller;

import com.springboot.jpastreamer.entity.Person;
import com.springboot.jpastreamer.exception.PersonNotFoundException;
import com.springboot.jpastreamer.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/persons/jpastreamer")
@RequiredArgsConstructor
public class PersonJpaStreamerController {

    private final PersonService personService;


    @GetMapping("/all")
    public ResponseEntity<List<Person>> findAll() {
        return new ResponseEntity(personService.findAllForJpaStreamer(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        Person person = personService.findByIdForJpaStreamer(id);
        if (null != person) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            throw new PersonNotFoundException(id);
        }
    }

    @GetMapping("/findByFirstCharacterOfFirstName/{character}")
    public ResponseEntity<List<Person>> findByFirstCharacterOfFirstName(@PathVariable String character) {
        List<Person> personList = personService.findByFirstCharacterOfFirstNameForJpaStreamer(character);
        if (!personList.isEmpty()) {
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } else {
            throw new PersonNotFoundException();
        }
    }

    @GetMapping("/findByAge/{age}")
    public ResponseEntity<List<Person>> findByAge(@PathVariable Integer age) {
        List<Person> personList = personService.findByAgeForJpaStreamer(age);
        if (!personList.isEmpty()) {
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } else {
            throw new PersonNotFoundException();
        }
    }

    @GetMapping("/findByLessThanAge/{age}")
    public ResponseEntity<List<Person>> findByLessThanAge(@PathVariable Integer age) {
        List<Person> personList = personService.findByLessThanAgeForJpaStreamer(age);
        if (!personList.isEmpty()) {
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } else {
            throw new PersonNotFoundException();
        }
    }

    @GetMapping("/findByFirstCharacterOfFirstNameAndAge/{character}/{age}")
    public ResponseEntity<List<Person>> findByFirstCharacterOfFirstNameAndAge(@PathVariable String character, @PathVariable Integer age) {
        List<Person> personList = personService.findByFirstCharacterOfFirstNameAndAgeForJpaStreamer(character, age);
        if (!personList.isEmpty()) {
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } else {
            throw new PersonNotFoundException();
        }
    }

    @GetMapping("/findByAgeRange/{age1}/{age2}")
    public ResponseEntity<List<Person>> findByAgeRange(@PathVariable Integer age1, @PathVariable Integer age2) {
        List<Person> personList = personService.findByAgeRangeForJpaStreamer(age1, age2);
        if (!personList.isEmpty()) {
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } else {
            throw new PersonNotFoundException();
        }
    }

    @GetMapping("/findMaximumAge")
    public ResponseEntity<Person> findMaximumAge() {
        Person person = personService.findMaximumAgeForJpaStreamer();
        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            throw new PersonNotFoundException();
        }
    }

    @GetMapping("/findMinimumAge")
    public ResponseEntity<Person> findMinimumAge() {
        Person person = personService.findMinimumAgeForJpaStreamer();
        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            throw new PersonNotFoundException();
        }
    }

    @GetMapping("/findByBirthdayRange/{startDate}/{endDate}")
    public ResponseEntity<List<Person>> findByBirthdayRange(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                          @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Person> personList = personService.findByBirthdayRangeForJpaStreamer(startDate, endDate);
        if (!personList.isEmpty()) {
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } else {
            throw new PersonNotFoundException();
        }
    }


}