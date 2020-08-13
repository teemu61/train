package com.example.demo3.controller;

import com.example.demo3.pojo.Result;
import com.example.demo3.pojo.Train;
import com.example.demo3.service.TrainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrainRestController {

    TrainService trainService;

    TrainRestController(TrainService trainService) {
        this.trainService = trainService;
    }

    @ApiOperation(value = "GET location of train. {id} is the train number.",
            notes="example: http://localhost:8080/api/trains/8448 ")
    @GetMapping(value = "/api/trains/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result getTrain(@PathVariable("id") Long id) throws Exception {

        Train train = trainService.getTrainById(id);
        return new Result(train);
    }
}



