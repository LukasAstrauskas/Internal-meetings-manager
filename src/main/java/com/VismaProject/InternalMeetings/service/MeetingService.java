package com.VismaProject.InternalMeetings.service;

import com.VismaProject.InternalMeetings.model.Meeting;
import com.VismaProject.InternalMeetings.model.Person;
import com.VismaProject.InternalMeetings.model.ResponseMessage;
import com.VismaProject.InternalMeetings.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public List<Meeting> getAllMeetings() {
        return meetingRepository.getAllMeetings();
    }

    public Meeting getMeetingById(Long id) {
        return meetingRepository.getMeetingById(id);
    }

    public void deleteMeeting(Long meetingID, Long personID) {
        Meeting meeting = getMeetingById(meetingID);
        if (meeting.getResponsiblePerson().getId().equals(personID)) {
            meetingRepository.delete(meeting);
        } else {
            System.out.println("Person with ID: " + personID + " isn't resp. for meet: " + meetingID);
        }
    }


    public void addMeeting(Meeting meeting) {
        Person responsiblePerson = meeting.getResponsiblePerson();
        insertPersonInMeeting(responsiblePerson, meeting);
        meetingRepository.addMeeting(meeting);
    }


    public ResponseEntity<ResponseMessage> addPersonToMeeting(Long meetingID, Person person) {
        Meeting meeting = getMeetingById(meetingID);
        ResponseEntity<ResponseMessage> response = insertPersonInMeeting(person, meeting);
        meetingRepository.updateMeeting(meetingID, meeting);
        return response;
    }


    public void removePersonFromMeeting(Long meetingID, Long personID) {
        Meeting meeting = getMeetingById(meetingID);
        HashSet<Person> persons = meeting.getPersons();
        Person person = persons.stream()
                .filter(p -> p.getId().equals(personID)).findFirst().orElseThrow();
        if (!meeting.getResponsiblePerson().getId().equals(personID)) {
            persons.remove(person);
            meeting.setPersons(persons);
            meetingRepository.updateMeeting(meetingID, meeting);
        }
    }

    private ResponseEntity<ResponseMessage> insertPersonInMeeting(Person person, Meeting meeting) {
        HashSet<Person> persons = meeting.getPersons();
        person.setDateAdded(new Date());
        persons.add(person);
        meeting.setPersons(persons);
        return checkForIntersections(person, meeting);
    }

    private ResponseEntity<ResponseMessage> checkForIntersections(Person person, Meeting meetingToAdd) {
        List<Meeting> allMeetings = getAllMeetings();
        allMeetings.remove(meetingToAdd);

        Set<String> meetingNames = allMeetings.stream()
                .filter(meeting ->meeting.getPersons().contains(person))
                .filter(meeting ->
                        meetingToAdd.getStartDate().before(meeting.getEndDate()) &&
                                meetingToAdd.getEndDate().after(meeting.getStartDate()))
                .map(Meeting::getName).collect(Collectors.toSet());

        String message = String.format("%s %s added to meeting %s.",
                person.getName(), person.getSurname(), meetingToAdd.getName());

        if (meetingNames.size() > 0) {
            message = message + " Time intersects with meetings!";
        } else {
            meetingNames.add(meetingToAdd.getName());
        }
        return new ResponseEntity<>(new ResponseMessage(message, meetingNames), HttpStatus.OK);
    }

    public boolean testForDescMatch(String description, String desc) {
        description = description.toLowerCase();
        desc = desc.toLowerCase();
        List<String> wordsList = List.of(desc.split("\\s"));
        for (String word : wordsList) {
            if (!description.contains(word)) {
                return false;
            }
        }
        return true;
    }
}
