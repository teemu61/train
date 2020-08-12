package com.example.demo3.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Data
public class Train {

    String trainNumber;
    String departureDate;
    Integer operatorUICCode;
    String trainType;
    String trainCagegory;
    String commuterLineID;
    Boolean runningCurrently;
    Boolean cancelled;
    Long version;
    String timetableType;
    String timetableAcceptanceDate;
    List<TimeTableRow> timeTableRows;
    @JsonIgnore
    LocalDateTime now = LocalDateTime.now();

    public List<TimeTableRow>  getArrivals() {

        List<TimeTableRow> arrivals = this.getTimeTableRows()
                .stream()
                .filter(i -> i.type.equals("ARRIVAL"))
                .sorted(Comparator.comparing(TimeTableRow::getActualTime))
                .collect(Collectors.toList());
        return arrivals;
    }

    public List<TimeTableRow>  getDepartures() {

        List<TimeTableRow> departures = this.getTimeTableRows()
                .stream()
                .filter(i -> i.type.equals("DEPARTURE"))
                .sorted(Comparator.comparing(TimeTableRow::getActualTime))
                .collect(Collectors.toList());
        return departures;
    }

}
