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

    public TrainMapper() {
        now = LocalDateTime.now();
    }

    public TrainDTO trainToTrainDto(Train train) {

        if (train == null)
            return null;

        TrainDTO trainDTO = new TrainDTO();
        findLatestArrival(train);
        findNextDeparture(train);

        if (lastestArrival != null && nextDeparture == null) {

            trainDTO.setCurrentStation(lastestArrival.stationShortCode);
            trainDTO.setArrival(lastestArrival.getScheduledTime());
            trainDTO.setCurrentTime(parseDate(now));
            log.info("no departure found");
        }

        if (lastestArrival != null && nextDeparture != null) {

            log.info("arrival and departure found");

            if (lastestArrival.getStationShortCode().equals(nextDeparture.getStationShortCode())) {
                trainDTO.setCurrentStation(lastestArrival.stationShortCode);
                trainDTO.setArrival(lastestArrival.getScheduledTime());
                trainDTO.setCurrentTime(parseDate(now));
                log.info("train is currently on station " +lastestArrival.getStationShortCode());

            } else {
                trainDTO.setFromStation(lastestArrival.getStationShortCode());
                trainDTO.setToStation(nextDeparture.getStationShortCode());
                trainDTO.setArrival(lastestArrival.getScheduledTime());
                trainDTO.setDeparture(nextDeparture.getScheduledTime());
                trainDTO.setCurrentTime(parseDate(now));
                log.info(String.format("train is currently between stations %s and %s",
                        lastestArrival.stationShortCode, nextDeparture.stationShortCode));
            }
         }
        return trainDTO;
    }

    private void findNextDeparture(Train train) {

        List<TimeTableRow> departures = getDepartures(train);

        for (TimeTableRow row : departures) {
            LocalDateTime date = parseStr(row.getScheduledTime());
            if (date.isAfter(now)) {
                this.nextDeparture = row;
                break;
            }
        }
    }

    private void findLatestArrival(Train train) {

        List<TimeTableRow> arrivals = getArrivals(train);

        for (TimeTableRow row: arrivals) {
            LocalDateTime date = parseStr(row.getScheduledTime());
            if (date.isBefore(now)) {
                this.lastestArrival = row;
            }
        }
    }

    private List<TimeTableRow>  getArrivals(Train train) {

        List<TimeTableRow> arrivals = train.getTimeTableRows()
                .stream()
                .filter(i -> i.type.equals("ARRIVAL"))
                .sorted(Comparator.comparing(TimeTableRow::getScheduledTime))
                .collect(Collectors.toList());

        return arrivals;
    }

    private List<TimeTableRow> getDepartures(Train train) {

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
