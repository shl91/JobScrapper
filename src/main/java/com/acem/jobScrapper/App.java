package com.acem.jobScrapper;

import com.acem.jobScrapper.dataTemplate.Job;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


public class App {
    public static void main(String[] args) {
        List<Job> finalList = Scrapper.scrapAll();

        ObjectMapper mapper = new ObjectMapper();
        for (Job job : finalList) {
            try {
                String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(job);
                System.out.println(jsonString);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}






