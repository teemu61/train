package com.example.demo3.service;

import com.example.demo3.exception.NoTrainsFoundException;
import com.example.demo3.pojo.TimeTableRowSummary;
import com.example.demo3.pojo.Train;
import com.example.demo3.pojo.TrainSummary;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainServiceImpl implements TrainService{

    private static final String BASE_URL = "https://rata.digitraffic.fi/api/v1";

    public Train getTrainById(Long id) {

        LocalDate today = LocalDate.now();
        String url = String.format("%s/trains/%s/%s", BASE_URL, today, id);

        RestTemplate restTemplate = getRestTemplate();

        Train[] trains = restTemplate.getForObject(url,Train[].class);
        if (trains == null || trains.length == 0)
            throw new NoTrainsFoundException("No train found for ID " +id );
        return trains[0];

        }

    public List<TrainSummary> getTrainSummaryList() {

        String url = BASE_URL +"/live-trains/station/HKI?arrived_trains=0" +
                "&arriving_trains=0&departed_trains=20&departing_trains=0" +
                "&include_nonstopping=false";

        RestTemplate restTemplate = getRestTemplate();
        TrainSummary[] trains = restTemplate.getForObject(url,TrainSummary[].class);
        return Arrays.asList(trains);

     }

    private RestTemplate getRestTemplate() {

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        return new RestTemplate(clientHttpRequestFactory);
    }

}

