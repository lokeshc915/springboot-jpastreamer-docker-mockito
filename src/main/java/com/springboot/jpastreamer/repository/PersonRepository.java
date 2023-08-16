package com.springboot.jpastreamer.repository;

import com.springboot.jpastreamer.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
