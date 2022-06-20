package com.VismaProject.InternalMeetings.controller;

import com.VismaProject.InternalMeetings.model.Meeting;
import com.VismaProject.InternalMeetings.model.Person;
import com.VismaProject.InternalMeetings.model.ResponseMessage;
import com.VismaProject.InternalMeetings.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/meetings")
    public List<Meeting> meetings(
            @RequestParam("meetingID") Optional<Long> meetingID,
            @RequestParam("desc") Optional<String> description,
            @RequestParam("respID") Optional<Long> respID,
            @RequestParam("category") Optional<String> category,
            @RequestParam("type") Optional<String> type,
            @RequestParam("from") Optional<String> from,
            @RequestParam("until") Optional<String> until,
            @RequestParam("over") Optional<Integer> over,
            @RequestParam("less") Optional<Integer> less
    ) {
        List<Meeting> allMeetings = meetingService.getAllMeetings();
        meetingID.ifPresent(id -> {
                    List<Meeting> toRemove = allMeetings.stream()
                            .filter(meeting -> !meeting.getId().equals(id))
                            .collect(Collectors.toList());
                    allMeetings.removeAll(toRemove);
                }
        );
        description.ifPresent(desc -> {
                    List<Meeting> toRemove = allMeetings.stream()
                            .filter(meeting ->
                                    !meetingService.testForDescMatch(meeting.getDescription(), desc))
                            .collect(Collectors.toList());
                    allMeetings.removeAll(toRemove);
                }
        );
        respID.ifPresent(id -> {
                    List<Meeting> toRemove = allMeetings.stream()
                            .filter(meeting ->
                                    !meeting.getResponsiblePerson().getId().equals(id))
                            .collect(Collectors.toList());
                    allMeetings.removeAll(toRemove);
                }
        );
        category.ifPresent(cat -> {
                    List<Meeting> toRemove = allMeetings.stream()
                            .filter(meeting ->
                                    !meeting.getCategory().toString()
                                            .equals(cat.toUpperCase()))
                            .collect(Collectors.toList());
                    allMeetings.removeAll(toRemove);
                }
        );
        type.ifPresent(t -> {
                    List<Meeting> toRemove = allMeetings.stream()
                            .filter(meeting -> !meeting.getType().toString()
                                    .equals(t.toUpperCase()))
                            .collect(Collectors.toList());
                    allMeetings.removeAll(toRemove);
                }
        );
        type.ifPresent(t -> {
                    List<Meeting> toRemove = allMeetings.stream()
                            .filter(meeting -> !meeting.getType().toString()
                                    .equals(t.toUpperCase()))
                            .collect(Collectors.toList());
                    allMeetings.removeAll(toRemove);
                }
        );
        from.ifPresent(dateString -> {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    try {
                        date = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date dateFrom = date;
                    List<Meeting> toRemove = allMeetings.stream()
                            .filter(meeting ->
                                    dateFrom.after(meeting.getStartDate())
                            )
                            .collect(Collectors.toList());
                    allMeetings.removeAll(toRemove);
                }
        );
        until.ifPresent(dateString -> {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    try {
                        date = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date dateUntil = date;
                    List<Meeting> toRemove = allMeetings.stream()
                            .filter(meeting ->
                                    dateUntil.before(meeting.getEndDate())
                            )
                            .collect(Collectors.toList());
                    allMeetings.removeAll(toRemove);
                }
        );
        over.ifPresent(number -> {
                    List<Meeting> toRemove = allMeetings.stream()
                            .filter(meeting ->
                                    !(meeting.getPersons().size() > number)
                            )
                            .collect(Collectors.toList());
                    allMeetings.removeAll(toRemove);
                }
        );
        less.ifPresent(number -> {
                    List<Meeting> toRemove = allMeetings.stream()
                            .filter(meeting ->
                                    !(meeting.getPersons().size() < number)
                            )
                            .collect(Collectors.toList());
                    allMeetings.removeAll(toRemove);
                }
        );
        return allMeetings;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addMeeting(@RequestBody Meeting meeting) {
        meetingService.addMeeting(meeting);
    }


    @PutMapping(value = "/add-person/{meetingID}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> addPersonToMeeting(
            @PathVariable(value = "meetingID") Long meetingID,
            @RequestBody Person person) {
        return meetingService.addPersonToMeeting(meetingID, person);
    }

    @PutMapping(value = "/remove-person/{meetingID}/{personID}")
    public void removePersonFromMeeting(@PathVariable(value = "meetingID") Long meetingID,
                                        @PathVariable(value = "personID") Long personID) {
        meetingService.removePersonFromMeeting(meetingID, personID);
    }


    @DeleteMapping(value = "/delete/{meetingID}/{personID}")
    public void deleteMeeting(@PathVariable(value = "meetingID") Long meetingID,
                              @PathVariable(value = "personID") Long personID) {
        meetingService.deleteMeeting(meetingID, personID);
    }

}
