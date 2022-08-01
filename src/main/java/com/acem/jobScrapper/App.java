package com.acem.jobScrapper;


import com.acem.jobScrapper.dataTemplate.Job;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Document;


import java.util.List;

import static com.acem.jobScrapper.Scrapper.getDoc;

public class App {
    public static void main(String[] args) {
        String url = Url.getUrl();
        Document document = getDoc(url);
        Scrapper.getNoOfJobs(document);
        /*List<Job> finalList = Scrapper.scrapAll();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(finalList);
            System.out.println(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }*/
    }
}






