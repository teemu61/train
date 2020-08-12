package com.example.demo3.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Slf4j
@Data
public class Result {

    private String currentStation;
    private String fromStation;
    private String toStation;
    private String arrival;
    private String departure;
    private String currentTime;

    @JsonIgnore
    private LocalDateTime now;

    @JsonIgnore
    private TimeTableRow nextDeparture;

    @JsonIgnore
    private TimeTableRow lastestArrival;

    public Result(Train train) {

        now = train.getNow();
        List<TimeTableRow> arrivals = train.getArrivals();
        List<TimeTableRow> departures = train.getDepartures();

        findLatestArrival(arrivals);
        findNextDeparture(departures);

        if (lastestArrival != null && nextDeparture == null) {

            this.currentStation = lastestArrival.stationShortCode;
            this.arrival = lastestArrival.actualTime;
            this.currentTime = parseDate(now);
            log.info("no departure found");

        }

        if (lastestArrival != null && nextDeparture != null) {

            log.info("arrival and departure found");

            if (lastestArrival.getStationShortCode().equals(nextDeparture.getStationShortCode())) {
                this.currentStation = lastestArrival.stationShortCode;
                this.arrival = lastestArrival.actualTime;
                this.currentTime = parseDate(now);
                log.info("train is currently on station " +lastestArrival.getStationShortCode());

            } else {
                this.fromStation = lastestArrival.getStationShortCode();
                this.toStation = nextDeparture.getStationShortCode();
                this.arrival = lastestArrival.getActualTime();
                this.departure = nextDeparture.actualTime;
                this.currentTime = parseDate(now);
                log.info(String.format("train is currently between stations %s and %s",
                        lastestArrival.stationShortCode, nextDeparture.stationShortCode));
            }
        }
    }

    private void findNextDeparture(List<TimeTableRow> departures) {
        for (TimeTableRow row : departures) {
            LocalDateTime date = parseStr(row.getActualTime());
            if (date.isAfter(now)) {
                this.nextDeparture = row;
                break;
            }
        }
    }

    private void findLatestArrival(List<TimeTableRow> arrivals) {
        for (TimeTableRow row: arrivals) {
            LocalDateTime date = parseStr(row.getActualTime());
            if (date.isBefore(now)) {
                this.lastestArrival = row;
            }
        }
    }


    public String parseDate(LocalDateTime now) {
        if (now == null) return "";

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return now.format(formatter);
    }

    private LocalDateTime parseStr(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse(string, formatter);
        return date;
    }

}
