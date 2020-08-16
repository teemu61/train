package com.example.demo3.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class TrainDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long trainNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currentStation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fromStation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String toStation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String arrival;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String departure;
    private String currentTime;
}
