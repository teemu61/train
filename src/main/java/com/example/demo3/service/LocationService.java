package com.example.demo3.service;

import com.example.demo3.pojo.TrainDTO;
import com.example.demo3.pojo.TrainSummary;

import java.util.List;

public interface LocationService {

    TrainDTO getTrainDtoById(Long id);

    List<TrainDTO> getTrainDtoList();

    List<TrainSummary> getTrainSummaryList();
}
