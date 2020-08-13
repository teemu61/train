package com.example.demo3.pojo;

import com.example.demo3.pojo.TimeTableRow;
import com.example.demo3.pojo.Train;
import com.example.demo3.pojo.TrainDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Data
public class TrainMapper {

    private LocalDateTime now;
    private TimeTableRow nextDeparture;
    private TimeTableRow lastestArrival;
    private TrainDTO trainDTO = new TrainDTO();
    private Train train;

    public TrainMapper() {
        now = LocalDateTime.now();
    }

    public TrainDTO trainToTrainDto(Train train) {

        if (train == null) {
            return null;
        } else {
            this.train = train;
        }

        findLatestArrival();
        findNextDeparture();

        if (lastestArrival != null && nextDeparture == null) {
            log.info("only latestArrival found. nextDeparture was null");
            updateTrainInCurrentStation();
        }

        if (lastestArrival != null && nextDeparture != null) {
            if (lastestArrival.getStationShortCode().equals(nextDeparture.getStationShortCode())) {
                updateTrainInCurrentStation();
            } else {
                updateTrainBetweenStations();
            }
         }
        return trainDTO;
    }

    private void updateTrainBetweenStations() {
        log.info(String.format("train is currently between stations %s and %s",
                lastestArrival.stationShortCode, nextDeparture.stationShortCode));
        trainDTO.setFromStation(lastestArrival.getStationShortCode());
        trainDTO.setToStation(nextDeparture.getStationShortCode());
        trainDTO.setArrival(lastestArrival.getScheduledTime());
        trainDTO.setDeparture(nextDeparture.getScheduledTime());
        trainDTO.setCurrentTime(parseDate(now));
    }

    private void updateTrainInCurrentStation() {
        log.info("train is currently on station " +lastestArrival.getStationShortCode());
        trainDTO.setCurrentStation(lastestArrival.stationShortCode);
        trainDTO.setArrival(lastestArrival.getScheduledTime());
        trainDTO.setCurrentTime(parseDate(now));
    }

    private void findNextDeparture() {

        List<TimeTableRow> departures = getDepartures();

        for (TimeTableRow row : departures) {
            LocalDateTime date = parseStr(row.getScheduledTime());
            if (date.isAfter(now)) {
                this.nextDeparture = row;
                break;
            }
        }
    }

    private void findLatestArrival() {

        List<TimeTableRow> arrivals = getArrivals();

        for (TimeTableRow row: arrivals) {
            LocalDateTime date = parseStr(row.getScheduledTime());
            if (date.isBefore(now)) {
                this.lastestArrival = row;
            }
        }
    }

    private List<TimeTableRow>  getArrivals() {

        List<TimeTableRow> arrivals = train.getTimeTableRows()
                .stream()
                .filter(i -> i.type.equals("ARRIVAL"))
                .sorted(Comparator.comparing(TimeTableRow::getScheduledTime))
                .collect(Collectors.toList());

        return arrivals;
    }

    private List<TimeTableRow> getDepartures() {

        List<TimeTableRow> departures = train.getTimeTableRows()
                .stream()
                .filter(i -> i.type.equals("DEPARTURE"))
                .sorted(Comparator.comparing(TimeTableRow::getScheduledTime))
                .collect(Collectors.toList());
        return departures;
    }

    private String parseDate(LocalDateTime now) {
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
