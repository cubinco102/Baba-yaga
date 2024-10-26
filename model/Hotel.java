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
public class Hotel implements Serializable {

    
    private String hotelID;
    private String name;
    private String location;
    private int roomAvailable;
    private List<String> amenities;
    private double pricing;

    public Hotel() {
    }

    public Hotel(String hotelID, String name, String location, int roomAvailable, List<String> amenities, double pricing) {
        this.hotelID = hotelID;
        this.name = name;
        this.location = location;
        this.roomAvailable = roomAvailable;
        this.amenities = amenities;
        this.pricing = pricing;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRoomAvailable() {
        return roomAvailable;
    }

    public void setRoomAvailable(int roomAvailable) {
        this.roomAvailable = roomAvailable;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public double getPricing() {
        return pricing;
    }

    public void setPricing(double pricing) {
        this.pricing = pricing;
    }

    @Override
    public String toString() {
        String result = "Hotel ID: " + hotelID + "\n" +
                        "Hotel Name: " + name + "\n" +
                        "Location: " + location + "\n" +
                        "Room Available: " + roomAvailable + "\n" +
                        "Pricing: $" + pricing + "\n" +
                        "Amenities:\n";
        
        for (String amenity : amenities) {
            result += "- " + amenity + "\n";
        }

        result += "------------------------------\n";
        return result;
    }

}
