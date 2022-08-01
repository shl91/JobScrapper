package com.acem.jobScrapper;


import com.acem.jobScrapper.dataTemplate.Job;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Document;


import java.util.List;

import static com.acem.jobScrapper.Scrapper.getDoc;

public class App {
    public static void main(String[] args) {
        List<Job> finalList = Scrapper.scrapAll();
        int round = finalList.size();

        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < round; i++) {
            try {
                String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(finalList.get(i));
                System.out.println(jsonString);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}






