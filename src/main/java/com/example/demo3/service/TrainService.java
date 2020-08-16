package com.example.demo3.service;

import com.example.demo3.exception.NoTrainsFoundException;
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

@Service
public class TrainService {


    public Train getTrainById(Long id) {

        LocalDate today = LocalDate.now();
        String url = String.format("https://rata.digitraffic.fi/api/v1/trains/%s/%s", today, id);

        RestTemplate restTemplate = getRestTemplate();
        getHeaders();

        Train[] trains = restTemplate.getForObject(url,Train[].class);
        if (trains == null || trains.length == 0)
            throw new NoTrainsFoundException("No train found for ID " +id );
        return trains[0];
        }

    public List<TrainSummary> getTrainSummary() {

        String url = "https://rata.digitraffic.fi/api/v1/live-trains/station/" +
                "HKI?arrived_trains=0&arriving_trains=0&departed_trains=20&departing_trains=0" +
                "&include_nonstopping=false";

        RestTemplate restTemplate = getRestTemplate();
        getHeaders();

         TrainSummary[] trains = restTemplate.getForObject(url,TrainSummary[].class);
         return Arrays.asList(trains);
     }

    public List<Train> getTrains() {

        List<Train> trainList = new ArrayList<>();
        List<TrainSummary> trainSummaryList = getTrainSummary();

        for (TrainSummary summary: trainSummaryList) {
            String trainNumber = summary.getTrainNumber();
            Train train = getTrainById(Long.parseLong(trainNumber));

            trainList.add(train);
        }
        return trainList;
    }

    private void getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accept-encoding", "application/gzip");
    }

    private RestTemplate getRestTemplate() {

        /*clientHttpRequestFactory supports gzip*/
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        return new RestTemplate(clientHttpRequestFactory);
    }

    }

