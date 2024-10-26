/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author huyqu
 */
public class Tour implements Serializable {

    private String tourID;
    private String name;
    private String detination;
    private int duration;
    private double price;
    private String description;
    private List<String> inclusions;
    private List<String> exclusions;

    public Tour() {
    }

    public Tour(String tourID, String name, String detination, int duration, double price, String description, List<String> inclusions, List<String> exclusions) {
        this.tourID = tourID;
        this.name = name;
        this.detination = detination;
        this.duration = duration;
        this.price = price;
        this.description = description;
        this.inclusions = inclusions;
        this.exclusions = exclusions;
    }

    public String getTourID() {
        return tourID;
    }

    public void setTourID(String tourID) {
        this.tourID = tourID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetination() {
        return detination;
    }

    public void setDetination(String detination) {
        this.detination = detination;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getInclusions() {
        return inclusions;
    }

    public void setInclusions(List<String> inclusions) {
        this.inclusions = inclusions;
    }

    public List<String> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<String> exclusions) {
        this.exclusions = exclusions;
    }

    @Override
    public String toString() {
        String result = "Tour ID: " + tourID + "\n"
                + "Tour Name: " + name + "\n"
                + "Destination: " + detination + "\n"
                + "Duration: " + duration + " days\n"
                + "Price: $" + price + "\n"
                + "Description: " + description + "\n"
                + "Inclusions:\n";

        for (String inclusion : inclusions) {
            result += "- " + inclusion + "\n";
        }

        result += "Exclusions:\n";
        for (String exclusion : exclusions) {
            result += "- " + exclusion + "\n";
        }

        result += "------------------------------\n";
        return result;
    }
}
