package com.example.demo3.service;

import com.example.demo3.pojo.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService{

    TrainService trainService;

    LocationServiceImpl(TrainService trainService) {
        this.trainService = trainService;
    }

    public TrainDTO getTrainDtoById(Long id) {

        Train train = trainService.getTrainById(id);
        TrainMapper mapper = new TrainMapper();
        return mapper.trainToTrainDto(train);
    }

    public List<TrainDTO> getTrainDtoList() {

        List<TrainSummary> trainSummaryList = getTrainSummaryList();

        List<Long> trainNumberList = trainSummaryList.stream()
                .map(i -> Long.parseLong(i.getTrainNumber()))
                .collect(Collectors.toList());

        List<Train> trainList = new ArrayList<>();
        trainNumberList.forEach(id -> trainList.add(trainService.getTrainById(id)));

        List<TrainDTO> trainDtoList = new ArrayList<>();
        TrainMapper mapper = new TrainMapper();
        trainList.forEach(train -> trainDtoList.add(mapper.trainToTrainDto(train)));
        return trainDtoList;
    }


    public List<TrainSummary> getTrainSummaryList() {

        List<TrainSummary> trainList = trainService.getTrainSummaryList();
        return filterStoppedTrains(trainList);
    }

    private List<TrainSummary> filterStoppedTrains(List<TrainSummary> trainList) {

        List<TrainSummary> trainListFiltered = new ArrayList<>();

        for (TrainSummary summary: trainList) {
            List<TimeTableRowSummary> rowListFilted = summary.getTimeTableRows()
                    .stream()
                    .filter(i -> i.getTrainStopping() != false)
                    .collect(Collectors.toList());
            summary.setTimeTableRows(rowListFilted);
            trainListFiltered.add(summary);
        }

        return trainListFiltered;
    }

}
