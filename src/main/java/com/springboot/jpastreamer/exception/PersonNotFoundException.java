package com.springboot.jpastreamer.exception;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException() {
        super("Could not find person.");
    }

    public PersonNotFoundException(Long id) {
        super("Could not find person " + id + ".");
    }
}
