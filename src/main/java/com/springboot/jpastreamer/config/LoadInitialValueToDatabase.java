package com.springboot.jpastreamer.config;

import com.github.javafaker.Faker;
import com.springboot.jpastreamer.entity.Person;
import com.springboot.jpastreamer.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@Slf4j
class LoadInitialValueToDatabase {

    @Bean
    CommandLineRunner initDatabase(PersonService personService) {
        return args -> {
            List<Person> personList = generateRandomPersons(100);
            personService.saveAllForJpaRepository(personList);
        };
    }

    private List<Person> generateRandomPersons(int numberOfPersons) {

        log.info("generateRandomPersons is started");

        List<Person> PersonList = new ArrayList<>();
        Faker faker = Faker.instance();

        for (int i = 0; i < numberOfPersons; i++) {
            Date birthdayDate = faker.date().birthday(0, 100);
            LocalDate birthday = birthdayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int age = Period.between(birthday, LocalDate.now()).getYears();

            Person person = Person.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .birthday(birthday)
                    .age(age)
                    .build();

            PersonList.add(person);
        }

        log.info("******************************");
        log.info(numberOfPersons + " fake persons were created.");
        log.info("******************************");

        return PersonList;
    }
}