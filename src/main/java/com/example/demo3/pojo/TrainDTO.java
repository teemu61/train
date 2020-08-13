package com.example.demo3.pojo;

import lombok.Data;

@Data
public class TrainDTO {
    private String currentStation;
    private String fromStation;
    private String toStation;
    private String arrival;
    private String departure;
    private String currentTime;
}
