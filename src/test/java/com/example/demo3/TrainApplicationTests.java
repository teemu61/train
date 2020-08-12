package com.example.demo3;

import com.example.demo3.pojo.Result;
import com.example.demo3.pojo.Train;
import com.example.demo3.service.TrainService;
import com.google.gson.Gson;
import org.junit.Test;

import org.junit.Before;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

//static imports
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TrainApplicationTests {

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    private Train initTrain() throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("test.json").getFile());
        String jsonData = new String(Files.readAllBytes(file.toPath()));
        Gson gson = new Gson();
        return gson.fromJson(jsonData, Train.class);
    }

    @Test
    public void testTrainBetweenStations() throws IOException {

        //given
        LocalDateTime now = LocalDateTime.of(2020,
                Month.JANUARY, 23, 07, 05, 00);
        Train train = initTrain();
        train.setNow(now);

        //when

        //then
        Result result = new Result(train);
        assertNotNull(result);
        assertEquals(result.getFromStation(), "STA1");
        assertEquals(result.getToStation(), "STA2");
    }

    @Test
    public void testTrainAtStation() throws IOException {

        //given
        LocalDateTime now = LocalDateTime.of(2020,
                Month.JANUARY, 23, 07, 01, 00);
        Train train = initTrain();
        train.setNow(now);

        //when

        //then
        Result result = new Result(train);
        assertNotNull(result);
        assertEquals(result.getCurrentStation(), "STA1");
    }

}
