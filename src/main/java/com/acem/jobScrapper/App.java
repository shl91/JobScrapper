package com.acem.jobScrapper;

import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {
    public static void main(String[] args) {
        String url = Url.getUrl();
        Document document = Scrapper.getDoc(url);
        int rounds = Scrapper.getNoOfPages(document);
        System.out.println(rounds);
        Scrapper.scrapCurrent(document);
        for(int i = 2; i<=rounds;i++){


        }

    }


    }






