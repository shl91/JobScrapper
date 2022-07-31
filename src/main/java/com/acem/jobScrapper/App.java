package com.acem.jobScrapper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {
    public static void main(String[] args) {
            try {
                Document document = Jsoup.connect("https://merojob.com/search/?q=java")
                        .timeout(5000)
                        .get();

                Element content = document.getElementById("content");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }




