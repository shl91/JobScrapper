package com.acem.jobScrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.acem.jobScrapper.dataTemplate.Company;
import com.acem.jobScrapper.dataTemplate.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;


public class Scrapper {
    public static Document getDoc(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .timeout(5000)
                    .get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static int getNoOfPages(Document doc){
        Elements pagNav = doc.getElementsByClass("page-item ");
        /* current and last page are in this class. Filtered them out. */
        if (!pagNav.hasText())//single page
            return 1;
        String no = pagNav.eachText().get(pagNav.eachText().size() - 1);
        return Integer.parseInt(no);

    }

    public static void scrapCurrent(Document doc){

        Elements jobTitle = doc.getElementsByClass("text-primary font-weight-bold media-heading h4");

        Elements companyName = doc.select("h3");

        Elements logoURL = doc.getElementsByClass("border p-1");
        Elements location = doc.getElementsByClass("location font-12");

        Elements deadline = doc.getElementsByClass("text-primary mb-0");


        Elements[] data = new Elements[] {jobTitle,companyName,location};

       int noOfElements = jobTitle.size();

       List<Job> jobsInAPage = new ArrayList<>();

        for (int i = 0;i<noOfElements;i++) {
            Company company = new Company();
            Job job = new Job();

            if(!jobTitle.get(i).hasText()) {
                job.setName("Not Provided");
            }else{
                job.setName(jobTitle.get(i).text());
            }


            company.setName(companyName.get(i).attr("title"));
            company.setLogoUrl(logoURL.get(i).attr("abs:src"));
            company.setLocation(location.get(i).text());

            job.setDeadline(deadline.get(i).select("meta").attr("content"));


            job.setCompany(company);

            jobsInAPage.add(job);

        }
        System.out.println(jobsInAPage);



       /*for (int i = 0;i<noOfElements;i++){
           job.setName(jobTitles.text());
           company.setName(companyName.text());
       }*/


    }

}
