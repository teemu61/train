package com.example.demo3.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class TrainReady {
    String source;
    Boolean accepted;
    Date timestamp;
}
