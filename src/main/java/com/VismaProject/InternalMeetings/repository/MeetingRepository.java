package com.VismaProject.InternalMeetings.repository;

import com.VismaProject.InternalMeetings.model.Meeting;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class MeetingRepository {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String pathName = "meetings.json";

    private ArrayList<Meeting> readJsonFile() {
        List<Meeting> list = List.of();
        if (Paths.get(pathName).toFile().exists()) {

            try {
                list = Arrays.asList(objectMapper.readValue(Paths.get(pathName).toFile(), Meeting[].class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            createNewFile();
        }
        return new ArrayList<>(list);
    }

    private void createNewFile() {
        try {
            objectMapper.writeValue(Paths.get(pathName).toFile(), new ArrayList<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToJsonFile(ArrayList<Meeting> meetings) {
        try {
            objectMapper.writeValue(Paths.get(pathName).toFile(), meetings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Meeting> getAllMeetings() {
        return readJsonFile();
    }

    public Meeting getMeetingById(Long id) {
        Optional<Meeting> optional = readJsonFile().stream()
                .filter(meeting -> meeting.getId().equals(id)).findFirst();
        return optional.orElseThrow();
    }

    public void delete(Meeting meeting) {
        ArrayList<Meeting> meetings = readJsonFile();
        meetings.remove(meeting);
        writeToJsonFile(meetings);
    }

    public void addMeeting(Meeting meeting) {
        ArrayList<Meeting> meetings = readJsonFile();
        meetings.add(meeting);
        writeToJsonFile(meetings);
    }

    public void updateMeeting(Long meetingID, Meeting meeting) {
        delete(getMeetingById(meetingID));
        addMeeting(meeting);
    }
}
