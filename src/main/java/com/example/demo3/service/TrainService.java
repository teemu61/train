package com.example.demo3.service;

import com.example.demo3.pojo.Train;
import com.example.demo3.pojo.TrainSummary;

import java.util.List;

public interface TrainService {

    public Train getTrainById(Long id);

    public List<TrainSummary> getTrainSummaryList();

}
