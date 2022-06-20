package com.VismaProject.InternalMeetings.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class Person {

    private Long id;
    private String name;
    private String surname;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Vilnius")
    private Date dateAdded;

    public Person() {
    }


    public Person(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateAdded = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Vilnius")
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id) && name.equals(person.name) && surname.equals(person.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }
}
