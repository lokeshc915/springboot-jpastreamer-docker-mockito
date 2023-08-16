package com.springboot.jpastreamer.service;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.springboot.jpastreamer.base.BaseServiceTest;
import com.springboot.jpastreamer.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest extends BaseServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private JPAStreamer jpaStreamer;

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
    public void testFindAllForJpaStreamer(){

        // when
        Mockito.when(jpaStreamer.stream(Person.class)).thenReturn(personList.stream());

        // then
        List<Person> actualResult = personService.findAllForJpaStreamer();

        // assert the result
        assertEquals(personList, actualResult);
        assertEquals(personList.size(), actualResult.size());

        // verify
        Mockito.verify(jpaStreamer, Mockito.times(1)).stream(Person.class);

    }

    @Test
    public void testFindByIdForJpaStreamer(){

        // given
        Long id = 2L;

        // when
        Mockito.when(jpaStreamer.stream(Person.class)).thenReturn(personList.stream());

        // then
        Person result = personService.findByIdForJpaStreamer(id);

        // assert the result
        assertEquals(personList.get(1), result);

        // verify
        Mockito.verify(jpaStreamer, Mockito.times(1)).stream(Person.class);

    }


    @Test
    public void testJpaStreamerFindByAge() {

        // given
        int age = 32;
        List<Person> collectedListMock = personList.stream()
                .filter(person -> person.getAge() == age)
                .collect(Collectors.toList());

        // when
        Mockito.when(jpaStreamer.stream(Person.class)).thenReturn(personList.stream());

        // then
        List<Person> actualPersons = personService.findByAgeForJpaStreamer(age);

        // assert the result
        assertEquals(collectedListMock, actualPersons);
        assertEquals(collectedListMock.size(), actualPersons.size());

        // assert each person's properties
        for (int i = 0; i < collectedListMock.size(); i++) {
            assertEquals(collectedListMock.get(i).getId(), actualPersons.get(i).getId());
            assertEquals(collectedListMock.get(i).getFirstName(), actualPersons.get(i).getFirstName());
            assertEquals(collectedListMock.get(i).getLastName(), actualPersons.get(i).getLastName());
            assertEquals(collectedListMock.get(i).getAge(), actualPersons.get(i).getAge());
            assertEquals(collectedListMock.get(i).getBirthday(), actualPersons.get(i).getBirthday());
        }

        // verify
        Mockito.verify(jpaStreamer, Mockito.times(1)).stream(Person.class);
    }

    @Test
    public void testFindByFirstCharacterOfFirstNameForJpaStreamer() {
        // given
        String character = "J";
        List<Person> filteredList = personList.stream()
                .filter(person -> person.getFirstName().startsWith(character))
                .collect(Collectors.toList());

        // when
        Mockito.when(jpaStreamer.stream(Person.class)).thenReturn(personList.stream());

        // then
        List<Person> result = personService.findByFirstCharacterOfFirstNameForJpaStreamer(character);

        // assert the result
        assertEquals(filteredList.size(), result.size());
        assertEquals(filteredList.get(0).getFirstName(), result.get(0).getFirstName());
        assertEquals(filteredList.get(0).getLastName(), result.get(0).getLastName());
        assertEquals(filteredList.get(0).getAge(), result.get(0).getAge());
        assertEquals(filteredList.get(0).getBirthday(), result.get(0).getBirthday());
        assertEquals(filteredList.get(1).getFirstName(), result.get(1).getFirstName());
        assertEquals(filteredList.get(1).getLastName(), result.get(1).getLastName());
        assertEquals(filteredList.get(1).getAge(), result.get(1).getAge());
        assertEquals(filteredList.get(1).getBirthday(), result.get(1).getBirthday());

        // verify
        Mockito.verify(jpaStreamer, Mockito.times(1)).stream(Person.class);
    }

    @Test
    public void testFindByLessThanAgeForJpaStreamer() {
        // given
        int age = 33;
        List<Person> filteredList = personList.stream()
                .filter(person -> person.getAge() < age)
                .collect(Collectors.toList());

        // when
        Mockito.when(jpaStreamer.stream(Person.class)).thenReturn(personList.stream());

        // then
        List<Person> result = personService.findByLessThanAgeForJpaStreamer(age);

        // assert the result
        assertEquals(filteredList, result);
        assertEquals(filteredList.size(), result.size());
        assertEquals(filteredList.get(0).getFirstName(), result.get(0).getFirstName());
        assertEquals(filteredList.get(0).getLastName(), result.get(0).getLastName());
        assertEquals(filteredList.get(0).getAge(), result.get(0).getAge());
        assertEquals(filteredList.get(0).getBirthday(), result.get(0).getBirthday());
        assertEquals(filteredList.get(1).getFirstName(), result.get(1).getFirstName());
        assertEquals(filteredList.get(1).getLastName(), result.get(1).getLastName());
        assertEquals(filteredList.get(1).getAge(), result.get(1).getAge());
        assertEquals(filteredList.get(1).getBirthday(), result.get(1).getBirthday());

        // verify
        Mockito.verify(jpaStreamer, Mockito.times(1)).stream(Person.class);

    }

    @Test
    public void testFindByFirstCharacterOfFirstNameAndAgeForJpaStreamer() {
        // given
        String character = "J";
        int age = 33;
        List<Person> filteredList = personList.stream()
                .filter(person -> person.getFirstName().startsWith(character) && person.getAge() == age)
                .collect(Collectors.toList());

        // when
        Mockito.when(jpaStreamer.stream(Person.class)).thenReturn(personList.stream());

        // When
        List<Person> result = personService.findByFirstCharacterOfFirstNameAndAgeForJpaStreamer(character, age);

        // assert the result
        assertEquals(filteredList, result);
        assertEquals(filteredList.size(), result.size());
        assertEquals(filteredList.get(0).getFirstName(), result.get(0).getFirstName());
        assertEquals(filteredList.get(0).getLastName(), result.get(0).getLastName());
        assertEquals(filteredList.get(0).getAge(), result.get(0).getAge());
        assertEquals(filteredList.get(0).getBirthday(), result.get(0).getBirthday());

        // verify
        Mockito.verify(jpaStreamer, Mockito.times(1)).stream(Person.class);
    }

    @Test
    public void testFindByAgeRangeForJpaStreamer() {
        // Given
        int age1 = 27;
        int age2 = 33;
        List<Person> filteredList = personList.stream()
                .filter(person -> person.getAge() >= age1 && person.getAge() < age2)
                .collect(Collectors.toList());

        Mockito.when(jpaStreamer.stream(Person.class)).thenReturn(personList.stream());

        // then
        List<Person> result = personService.findByAgeRangeForJpaStreamer(age1, age2);

        // assert the result
        assertEquals(filteredList, result);
        assertEquals(filteredList.size(), result.size());

        // verify
        Mockito.verify(jpaStreamer, Mockito.times(1)).stream(Person.class);
    }

    @Test
    public void testFindMaximumAgeForJpaStreamer() {
        // Given
        Person maxAgePerson = personList.stream()
                .max(Comparator.comparing(Person::getAge))
                .orElse(null);

        // when
        Mockito.when(jpaStreamer.stream(Person.class)).thenReturn(personList.stream());

        // When
        Person result = personService.findMaximumAgeForJpaStreamer();

        // assert the result
        assertEquals(maxAgePerson, result);

        // verify
        Mockito.verify(jpaStreamer, Mockito.times(1)).stream(Person.class);
    }

    @Test
    public void testFindMinimumAgeForJpaStreamer() {
        // Given
        Person minAgePerson = personList.stream()
                .min(Comparator.comparing(Person::getAge))
                .orElse(null);

        // when
        Mockito.when(jpaStreamer.stream(Person.class)).thenReturn(personList.stream());

        // When
        Person result = personService.findMinimumAgeForJpaStreamer();

        // assert the result
        assertEquals(minAgePerson, result);

        // verify
        Mockito.verify(jpaStreamer, Mockito.times(1)).stream(Person.class);
    }

    @Test
    public void testJpaStreamerFindByBirthdayRange() {
        // Given
        LocalDate startDate = LocalDate.of(1990, 1, 1);
        LocalDate endDate = LocalDate.of(1996, 12, 31);
        List<Person> filteredList = personList.stream()
                .filter(person -> person.getBirthday().isAfter(startDate) && person.getBirthday().isBefore(endDate))
                .collect(Collectors.toList());

        Mockito.when(jpaStreamer.stream(Person.class)).thenReturn(personList.stream());

        // When
        List<Person> result = personService.findByBirthdayRangeForJpaStreamer(startDate, endDate);

        // assert the result
        assertEquals(filteredList, result);

        // verify
        Mockito.verify(jpaStreamer, Mockito.times(1)).stream(Person.class);
    }

}