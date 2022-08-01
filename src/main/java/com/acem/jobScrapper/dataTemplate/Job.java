package com.acem.jobScrapper.dataTemplate;

import java.util.Date;

public class Job {
    private String name;
    private String deadline;
    private Company company;


    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "\nJob{" +
                "name='" + name + '\'' +
                ", company=" + company +
                ", deadline=" + deadline +
                '}';
    }
}
