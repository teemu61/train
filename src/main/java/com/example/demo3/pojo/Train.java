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

    Long trainNumber;
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

}
