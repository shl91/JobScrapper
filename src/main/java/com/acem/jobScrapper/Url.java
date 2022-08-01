package com.acem.jobScrapper;

import java.util.Scanner;

public class Url {
    public static String getUrl(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Job title: ");
        String searchPhrase = scanner.nextLine();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://merojob.com/search/?q=");
        stringBuilder.append(searchPhrase);

        return stringBuilder.toString();
    }
}

