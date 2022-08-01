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
        /* Portal and data of current and last page are in this class. Filtered them out. */
        if (pagNav.isEmpty()){
            System.out.println("No job offer under that title.\nTry Another.");
            return 0;
        }
        if (!pagNav.hasText())//single page
            return 1;
        System.out.println(pagNav.eachText().get(1));
        return Integer.parseInt(pagNav.eachText().get(1));
        /*eachText returns list of string containing text of current and last page
        Accessed text of last page with get(10,then parsed*/
    }

    /*public static int getNoOfJobs(Document doc){
        Elements noOfJobs = doc.getElementsByClass("h6");
        String no = noOfJobs.get(0).text().substring(21);
        return Integer.parseInt(no);
    }*///finds total no of jobs, could be used for quality assurance

    public static List<Job> scrapCurrent(Document doc){
        Elements jobTitle = doc.getElementsByClass("text-primary font-weight-bold media-heading h4");

        Elements companyName = doc.select("h3");
        Elements logoURL = doc.getElementsByClass("border p-1");
        Elements location = doc.getElementsByClass("location font-12");

        Elements deadline = doc.getElementsByClass("text-primary mb-0");

        int noOfElements = jobTitle.size();

        List<Job> jobsInAPage = new ArrayList<>();

        for (int i = 0;i<noOfElements;i++) {
            String[] data = new String[]{jobTitle.get(i).text(),companyName.get(i).attr("title"),
                    logoURL.get(i).attr("abs:src"),location.get(i).text(),
                    deadline.get(i).select("meta").attr("content")};

            Company company = new Company();
            Job job = new Job();

            int j = 0;
            for (String datum : data) {
                if (datum.length() == 0)
                    datum = "Not Provided";
                switch (j){
                    case 0:
                        job.setName(datum);
                        break;
                    case 1:
                        company.setName(datum);
                        break;
                    case 2:
                        company.setLogoUrl(datum);
                        break;
                    case 3:
                        company.setLocation(datum);
                        break;
                    case 4:
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
