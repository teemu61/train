package com.example.demo3.service;

import com.example.demo3.pojo.Train;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class TrainService {


    public Train getTrainById(Long id) {

        LocalDate today = LocalDate.now();
        String url = String.format("https://rata.digitraffic.fi/api/v1/trains/%s/%s", today, id);

        /*clientHttpRequestFactory supports gzip*/
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accept-encoding", "application/gzip");

       /* map data from rest api to Train array*/
        Train[] trains = restTemplate.getForObject(url,Train[].class);
        return trains[0];
        }

    }

