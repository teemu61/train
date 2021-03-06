package com.example.demo3.controller;

import com.example.demo3.exception.NoTrainsFoundException;
import com.example.demo3.pojo.TrainDTO;
import com.example.demo3.pojo.Train;
import com.example.demo3.pojo.TrainMapper;
import com.example.demo3.pojo.TrainSummary;
import com.example.demo3.service.LocationService;
import com.example.demo3.service.TrainService;
import com.example.demo3.service.TrainServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TrainRestController {

    TrainService trainService;
    LocationService locationService;

    TrainRestController(TrainServiceImpl trainService, LocationService locationService) {
        this.trainService = trainService;
        this.locationService = locationService;
    }

    @ApiOperation(
            value = "GET location of train. {id} is the train number.",
            notes = "used for production"
    )
    @GetMapping(value = "/api/trains/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TrainDTO getTrain(@PathVariable("id")  Long id) throws Exception {

        return locationService.getTrainDtoById(id);
    }

    @ApiOperation(
            value = "GET summary of trains recently departed from Helsinki station ",
            notes = "used for integration testing"
    )
    @GetMapping(value = "/api/trains/summary")
    @ResponseStatus(HttpStatus.OK)
    public  List<TrainSummary> getTrainSummary() {

        return  locationService.getTrainSummaryList();
    }

    @ApiOperation(
            value="GET status of all trains recently departed from Helsinki station",
            notes ="used for integration testing"
    )
    @GetMapping(value = "/api/trains/list")
    public List<TrainDTO> getTrains() {

        return locationService.getTrainDtoList();
    }

    @ExceptionHandler({ NoTrainsFoundException.class })
    public String handleException(Exception exception) {

        return exception.getMessage();
    }

}



