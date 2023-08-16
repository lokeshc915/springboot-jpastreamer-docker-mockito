package com.springboot.jpastreamer.controller;

import com.springboot.jpastreamer.base.BaseControllerTest;
import com.springboot.jpastreamer.entity.Person;
import com.springboot.jpastreamer.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PersonJpaStreamerControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private List<Person> personList;

    @BeforeEach
    public void setup() {
        personList = Arrays.asList(
                Person.builder().id(1L).firstName("John").lastName("Doe").age(33).birthday(LocalDate.of(1990, 5, 15)).build(),
                Person.builder().id(2L).firstName("Jane").lastName("Smith").age(27).birthday(LocalDate.of(1996, 9, 20)).build(),
                Person.builder().id(3L).firstName("David").lastName("Johnson").age(32).birthday(LocalDate.of(1991, 9, 20)).build()
        );
    }

    @Test
    public void testFindAll() throws Exception {

        when(personService.findAllForJpaStreamer()).thenReturn(personList);

        mockMvc.perform(get("/persons/jpastreamer/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(personList.size()))
                .andExpect(jsonPath("$[0].id").value(personList.get(0).getId()))
                .andExpect(jsonPath("$[0].firstName").value(personList.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(personList.get(0).getLastName()))
                .andExpect(jsonPath("$[0].age").value(personList.get(0).getAge()))
                .andExpect(jsonPath("$[0].birthday").value(personList.get(0).getBirthday().toString()))
                .andExpect(jsonPath("$[1].id").value(personList.get(1).getId()))
                .andExpect(jsonPath("$[1].firstName").value(personList.get(1).getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(personList.get(1).getLastName()))
                .andExpect(jsonPath("$[1].age").value(personList.get(1).getAge()))
                .andExpect(jsonPath("$[1].birthday").value(personList.get(1).getBirthday().toString()))
                .andExpect(jsonPath("$[2].id").value(personList.get(2).getId()))
                .andExpect(jsonPath("$[2].firstName").value(personList.get(2).getFirstName()))
                .andExpect(jsonPath("$[2].lastName").value(personList.get(2).getLastName()))
                .andExpect(jsonPath("$[2].age").value(personList.get(2).getAge()))
                .andExpect(jsonPath("$[2].birthday").value(personList.get(2).getBirthday().toString()));
    }

    @Test
    public void testFindById() throws Exception {
        Long id = 1L;
        Person person = personList.get(0);
        when(personService.findByIdForJpaStreamer(id)).thenReturn(person);

        mockMvc.perform(get("/persons/jpastreamer/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(person.getId()))
                .andExpect(jsonPath("$.firstName").value(person.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(person.getLastName()))
                .andExpect(jsonPath("$.age").value(person.getAge()))
                .andExpect(jsonPath("$.birthday").value(person.getBirthday().toString()));
    }

    @Test
    public void testFindByFirstCharacterOfFirstName() throws Exception {
        String character = "J";
        List<Person> filteredList = personList.stream()
                .filter(person -> person.getFirstName().startsWith(character))
                .collect(Collectors.toList());
        when(personService.findByFirstCharacterOfFirstNameForJpaStreamer(character)).thenReturn(filteredList);

        mockMvc.perform(get("/persons/jpastreamer/findByFirstCharacterOfFirstName/{character}", character))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(filteredList.size()))
                .andExpect(jsonPath("$[0].id").value(filteredList.get(0).getId()))
                .andExpect(jsonPath("$[0].firstName").value(filteredList.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(filteredList.get(0).getLastName()))
                .andExpect(jsonPath("$[0].age").value(filteredList.get(0).getAge()))
                .andExpect(jsonPath("$[0].birthday").value(filteredList.get(0).getBirthday().toString()));
    }

    @Test
    public void testFindByAge() throws Exception {
        Integer age = 27;
        List<Person> filteredList = personList.stream()
                .filter(person -> person.getAge().equals(age))
                .collect(Collectors.toList());
        when(personService.findByAgeForJpaStreamer(age)).thenReturn(filteredList);

        mockMvc.perform(get("/persons/jpastreamer/findByAge/{age}", age))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(filteredList.size()))
                .andExpect(jsonPath("$[0].id").value(filteredList.get(0).getId()))
                .andExpect(jsonPath("$[0].firstName").value(filteredList.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(filteredList.get(0).getLastName()))
                .andExpect(jsonPath("$[0].age").value(filteredList.get(0).getAge()))
                .andExpect(jsonPath("$[0].birthday").value(filteredList.get(0).getBirthday().toString()));
    }

    @Test
    public void testFindByLessThanAge() throws Exception {
        Integer age = 33;
        List<Person> filteredList = personList.stream()
                .filter(person -> person.getAge() < age)
                .collect(Collectors.toList());
        when(personService.findByLessThanAgeForJpaStreamer(age)).thenReturn(filteredList);

        mockMvc.perform(get("/persons/jpastreamer/findByLessThanAge/{age}", age))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(filteredList.size()))
                .andExpect(jsonPath("$[0].id").value(filteredList.get(0).getId()))
                .andExpect(jsonPath("$[0].firstName").value(filteredList.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(filteredList.get(0).getLastName()))
                .andExpect(jsonPath("$[0].age").value(filteredList.get(0).getAge()))
                .andExpect(jsonPath("$[0].birthday").value(filteredList.get(0).getBirthday().toString()));
    }

    @Test
    public void testFindByFirstCharacterOfFirstNameAndAge() throws Exception {
        String character = "D";
        Integer age = 32;
        List<Person> filteredList = personList.stream()
                .filter(person -> person.getFirstName().startsWith(character) && person.getAge().equals(age))
                .collect(Collectors.toList());
        when(personService.findByFirstCharacterOfFirstNameAndAgeForJpaStreamer(character, age)).thenReturn(filteredList);

        mockMvc.perform(get("/persons/jpastreamer/findByFirstCharacterOfFirstNameAndAge/{character}/{age}", character, age))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(filteredList.size()))
                .andExpect(jsonPath("$[0].id").value(filteredList.get(0).getId()))
                .andExpect(jsonPath("$[0].firstName").value(filteredList.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(filteredList.get(0).getLastName()))
                .andExpect(jsonPath("$[0].age").value(filteredList.get(0).getAge()))
                .andExpect(jsonPath("$[0].birthday").value(filteredList.get(0).getBirthday().toString()));
    }

    @Test
    public void testFindByAgeRange() throws Exception {
        Integer age1 = 30;
        Integer age2 = 35;
        List<Person> filteredList = personList.stream()
                .filter(person -> person.getAge() >= age1 && person.getAge() <= age2)
                .collect(Collectors.toList());
        when(personService.findByAgeRangeForJpaStreamer(age1, age2)).thenReturn(filteredList);

        mockMvc.perform(get("/persons/jpastreamer/findByAgeRange/{age1}/{age2}", age1, age2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(filteredList.size()))
                .andExpect(jsonPath("$[0].id").value(filteredList.get(0).getId()))
                .andExpect(jsonPath("$[0].firstName").value(filteredList.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(filteredList.get(0).getLastName()))
                .andExpect(jsonPath("$[0].age").value(filteredList.get(0).getAge()))
                .andExpect(jsonPath("$[0].birthday").value(filteredList.get(0).getBirthday().toString()));
    }

    @Test
    public void testFindMaximumAge() throws Exception {
        Person maxAgePerson = personList.stream()
                .max(Comparator.comparingInt(Person::getAge))
                .orElseThrow(NoSuchElementException::new);
        when(personService.findMaximumAgeForJpaStreamer()).thenReturn(maxAgePerson);

        mockMvc.perform(get("/persons/jpastreamer/findMaximumAge"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(maxAgePerson.getId()))
                .andExpect(jsonPath("$.firstName").value(maxAgePerson.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(maxAgePerson.getLastName()))
                .andExpect(jsonPath("$.age").value(maxAgePerson.getAge()))
                .andExpect(jsonPath("$.birthday").value(maxAgePerson.getBirthday().toString()));
    }

    @Test
    public void testFindMinimumAge() throws Exception {
        Person minAgePerson = personList.stream()
                .max(Comparator.comparingInt(Person::getAge))
                .orElseThrow(NoSuchElementException::new);
        when(personService.findMinimumAgeForJpaStreamer()).thenReturn(minAgePerson);

        mockMvc.perform(get("/persons/jpastreamer/findMinimumAge"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(minAgePerson.getId()))
                .andExpect(jsonPath("$.firstName").value(minAgePerson.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(minAgePerson.getLastName()))
                .andExpect(jsonPath("$.age").value(minAgePerson.getAge()))
                .andExpect(jsonPath("$.birthday").value(minAgePerson.getBirthday().toString()));
    }


    @Test
    public void testFindByBirthdayRange() throws Exception {
        LocalDate startDate = LocalDate.of(1990, 1, 1);
        LocalDate endDate = LocalDate.of(1995, 12, 31);
        List<Person> filteredList = personList.stream()
                .filter(person -> person.getBirthday().compareTo(startDate) >= 0 && person.getBirthday().compareTo(endDate) <= 0)
                .collect(Collectors.toList());
        when(personService.findByBirthdayRangeForJpaStreamer(startDate, endDate)).thenReturn(filteredList);

        mockMvc.perform(get("/persons/jpastreamer/findByBirthdayRange/{startDate}/{endDate}", startDate, endDate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(filteredList.size()))
                .andExpect(jsonPath("$[0].id").value(filteredList.get(0).getId()))
                .andExpect(jsonPath("$[0].firstName").value(filteredList.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(filteredList.get(0).getLastName()))
                .andExpect(jsonPath("$[0].age").value(filteredList.get(0).getAge()))
                .andExpect(jsonPath("$[0].birthday").value(filteredList.get(0).getBirthday().toString()));
    }
}