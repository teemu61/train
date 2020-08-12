package com.example.demo3.controller;

import com.example.demo3.pojo.Result;
import com.example.demo3.pojo.Train;
import com.example.demo3.service.TrainService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainRestController {

    TrainService trainService;

    TrainRestController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping(value = "/api/trains/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result getTrain(@PathVariable("id") Long id) throws Exception {

        Train train = trainService.getTrainById(id);

        return new Result(train);


    }

}



