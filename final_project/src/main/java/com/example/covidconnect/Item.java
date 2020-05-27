package com.example.covidconnect;

public class Item {

    private String country;
    private String infected;
    private String cure;
    private String death;
    private String country_code;
    private String recovered;
    private String date;
    private String newDeaths;
    private String newCases;


    public Item(String country, String infected, String cure, String death, String country_code,
                String recovered, String date, String newDeaths, String newCases){
        this.country = country;
        this.infected = infected;
        this.cure = cure;
        this.death =death;
        this.country_code = country_code;
        this.recovered = recovered;
        this.date = date;
        this.newDeaths = newDeaths;
        this.newCases = newCases;

    }

    public String getCountry() {
        return country;
    }

    public String getInfected() {
        return infected;
    }

    public String getCure() {
        return cure;
    }

    public String getDeath() {
        return death;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDate() {
        return date;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public String getNewCases() {
        return newCases;
    }
}
