package com.example.demo3.pojo;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TimeTableRow {

    String stationShortCode;
    String stationUICCode;
    String countryCode;
    String type;
    Boolean trainStopping;
    Boolean commercialStop;
    String commercialTrack;
    Boolean cancelled;
    String scheduledTime;
    String actualTime;
    Long differenceInMinutes;
    List<Cause> causes;
    TrainReady trainReady;

}


