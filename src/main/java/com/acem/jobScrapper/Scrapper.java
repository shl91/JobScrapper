package com.acem.jobScrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.acem.jobScrapper.dataTemplate.Company;
import com.acem.jobScrapper.dataTemplate.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



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
        Elements checkNoJob = doc.getElementsByClass("h6");
        /* Portal and data of current and last page are in this class. Filtered them out.
        We know this list has only two elements.*/
        if (checkNoJob.size()<7){
            System.out.println("No job offer under that title.\nTry Another.");
            /*In case no entry*/
            return 0;
        }
        if (pagNav.isEmpty())//single page
            return 1;
        return Integer.parseInt(pagNav.eachText().get(1));
        /*eachText returns list of string containing text of current and last page.
        Accessed text of last page with get(1),then parsed*/
    }

    /*public static int getNoOfJobs(Document doc){
        Elements noOfJobs = doc.getElementsByClass("h6");
        String no = noOfJobs.get(0).text().substring(21);
        return Integer.parseInt(no);
    }*///finds total no of jobs, could be used for quality assurance?

    public static List<Job> scrapCurrent(Document doc){
        List<Job> jobsInAPage = new ArrayList<>();

        Elements jobTitle = doc.getElementsByClass("text-primary font-weight-bold media-heading h4");

        Elements logoURL = doc.getElementsByClass("border p-1");

        Elements deadline = doc.getElementsByClass("text-primary mb-0");
        int noOfElements = jobTitle.size();//Minimum requirement to offer job.

        Elements blocks = doc.getElementsByClass("col-8 col-lg-9 col-md-9 pl-3 pl-md-0 text-left");

        for (int i = 0;i<noOfElements;i++) {
            String[] data = new String[]{
                    jobTitle.get(i).text(),
                    logoURL.get(i).attr("abs:src"),
                    deadline.get(i).select("meta").attr("content")};

            Company company = new Company();
            Job job = new Job();

            //for name of company
            if(blocks.get(i).select("h3").size()==0){
                company.setName("Not Provided.");
            }else{
                company.setName(blocks.get(i).select("h3").attr("title"));
            }

            //for location
            if(blocks.get(i).getElementsByClass("location font-12").size()==0){
                company.setLocation("Not Provided.");
            }else{
                company.setLocation(blocks.get(i).
                        getElementsByClass("location font-12").get(0).
                        select("meta").attr("content"));
                /*each entry is in a block(line 60).
                Enter the block (line 75)
                and search specifically the class of location(line 76).
                Access "content" attribute(line 77).*/
            }

            int j = 0;
            for (String datum : data) {
                switch (j){
                    case 0:
                        job.setName(datum);
                        break;
                    case 1:
                        company.setLogoUrl(datum);
                        break;
                    case 2:
                        job.setDeadline(datum);
                        break;
                }
                j++;
            }
            job.setCompany(company);

            jobsInAPage.add(job);
        }
        return jobsInAPage;
    }

    public static List<Job> scrapAll() {
        List<Job> processing, ultimateList;
        int rounds;
        Document document;
        String url;

        do {
            url = Url.getUrl();
            document = getDoc(url);
            rounds = getNoOfPages(document);
        } while (rounds == 0);

        if (rounds == 1)
            return scrapCurrent(document);

        ultimateList = scrapCurrent(document);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("&page=");

        for (int i = 2; i <= rounds; i++) {
            document = getDoc(String.valueOf(stringBuilder) + i);
            processing = scrapCurrent(document);
            ultimateList.addAll(processing);
        }
        return ultimateList;
    }

}
