package com.example.demo3;

import com.example.demo3.pojo.Train;
import com.example.demo3.pojo.TrainDTO;
import com.example.demo3.pojo.TrainMapper;
import com.google.gson.Gson;
import org.junit.Test;

//static imports
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;


public class TrainApplicationTests {


    private Train initTrainInstance() throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("test.json").getFile());
        String jsonData = new String(Files.readAllBytes(file.toPath()));
        Gson gson = new Gson();
        return gson.fromJson(jsonData, Train.class);
    }

    @Test
    public void testTrainBetweenStations() throws IOException {

        //given
        LocalDateTime NOW = LocalDateTime.of(2020,
                Month.JANUARY, 23, 07, 05, 00);
        Train train = initTrainInstance();
        TrainMapper mapper = new TrainMapper();
        mapper.setNow(NOW);

        //when
        TrainDTO trainDTO = mapper.trainToTrainDto(train);

        //then
        assertNotNull(trainDTO);
        assertEquals("STA1", trainDTO.getFromStation());
        assertEquals("STA2", trainDTO.getToStation());
        assertEquals("between_stations", trainDTO.getStatus());
    }

    @Test
    public void testTrainAtLastStation() throws IOException {

        //given
        LocalDateTime NOW = LocalDateTime.of(2020,
                Month.JANUARY, 23, 07, 22, 00);
        Train train = initTrainInstance();
        TrainMapper mapper = new TrainMapper();
        mapper.setNow(NOW);

        //when
        TrainDTO trainDTO = mapper.trainToTrainDto(train);

        //then
        assertNotNull(trainDTO);
        assertEquals("STA3", trainDTO.getCurrentStation());
        assertEquals("last_station", trainDTO.getStatus());
    }

    @Test
    public void testTrainAtStation() throws IOException {

        //given
        LocalDateTime NOW = LocalDateTime.of(2020,
                Month.JANUARY, 23, 07, 01, 00);
        Train train = initTrainInstance();
        TrainMapper mapper = new TrainMapper();
        mapper.setNow(NOW);

        //when
        TrainDTO trainDTO = mapper.trainToTrainDto(train);

        //then
        assertNotNull(trainDTO);
        assertEquals("STA1", trainDTO.getCurrentStation());
    }



}
