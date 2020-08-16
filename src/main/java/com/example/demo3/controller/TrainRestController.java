package com.example.demo3.controller;

import com.example.demo3.exception.NoTrainsFoundException;
import com.example.demo3.pojo.TrainDTO;
import com.example.demo3.pojo.Train;
import com.example.demo3.pojo.TrainMapper;
import com.example.demo3.pojo.TrainSummary;
import com.example.demo3.service.TrainService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrainRestController {

    TrainService trainService;

    TrainRestController(TrainService trainService) {
        this.trainService = trainService;
    }

    @ApiOperation(
            value = "GET location of train. {id} is the train number.",
            notes="example: http://localhost:8080/api/trains/8448 "
    )
    @GetMapping(value = "/api/trains/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TrainDTO getTrain(@PathVariable("id")  Long id) throws Exception {

        Train train = trainService.getTrainById(id);
        TrainMapper mapper = new TrainMapper();
        TrainDTO trainDTO = mapper.trainToTrainDto(train, id);
        return trainDTO;
    }

    @ApiOperation(
            value = "GET summary of trains recently departed from Helsinki station ",
            notes = "used for testing purpose"
    )
    @GetMapping(value = "/api/trains/summary")
    @ResponseStatus(HttpStatus.OK)
    public  List<TrainSummary> getTrainSummary() {

        List<TrainSummary> trains = trainService.getTrainSummary();
        return trains;

    }

    @ExceptionHandler({ NoTrainsFoundException.class })
    public String handleException(Exception exception) {
        return exception.getMessage();
    }

}



