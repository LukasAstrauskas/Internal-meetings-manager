package com.VismaProject.InternalMeetings.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

public class Meeting {
    private Long id;
    private String name;
    private Person responsiblePerson;
    private String description;
    private Category category;
    private Type type;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Europe/Vilnius")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Europe/Vilnius")
    private Date endDate;
    private HashSet<Person> persons = new HashSet<>();
    ;

    public Meeting() {
    }

    public Meeting(Long id, String name, Person responsiblePerson,
                   String description, Category category, Type type,
                   Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.responsiblePerson = responsiblePerson;
        this.description = description;
        this.category = category;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Person responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public HashSet<Person> getPersons() {
        return persons;
    }

    public void setPersons(HashSet<Person> persons) {
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id) &&
                Objects.equals(name, meeting.name) &&
                Objects.equals(description, meeting.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, responsiblePerson, description, persons);
    }
}
