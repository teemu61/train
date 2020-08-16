package com.example.demo3.pojo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TrainSummary {

    String trainNumber;
    LocalDateTime currentTime = LocalDateTime.now();
    List<TimeTableRowSummary> timeTableRows;

}
