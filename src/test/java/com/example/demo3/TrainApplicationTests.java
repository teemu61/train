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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TrainApplicationTests {

//    @Before
//    public void setUp() throws IOException {
//        MockitoAnnotations.initMocks(this);
//    }

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
