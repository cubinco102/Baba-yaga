/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.io.Serializable;
import java.util.List;
import java.time.LocalDate;


/**
 *
 * @author huyqu
 */
public class Booking implements Serializable {

    private String bookingID;
    private String customerName;
    private int contactNumber;
    private Hotel bookedHotel;
    private Tour bookedTour;
    private int numberOfGuests;
    private double totalPrice;
    private List<String> customers;
    private List<String> policies;
    private LocalDate dayBooking;
    private LocalDate dayCheckIn;
    private LocalDate dayCheckOut;    
    

    public Booking() {
    }

    public Booking(String bookingID, String customerName, int contactNumber, Hotel bookedHotel, Tour bookedTour, int numberOfGuests,
            List<String> customers, LocalDate dayBooking, LocalDate dayCheckIn, LocalDate dayCheckOut) {
        this.bookingID = bookingID;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.bookedHotel = bookedHotel;
        this.bookedTour = bookedTour;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = calculateTotalPrice();
        this.customers = customers;
        this.dayBooking = dayBooking;
        this.dayCheckIn = dayCheckIn;
        this.dayCheckOut = dayCheckOut;
    }

    public LocalDate getDayBooking() {
        return dayBooking;
    }

    public void setDayBooking(LocalDate dayBooking) {
        this.dayBooking = dayBooking;
    }

    public LocalDate getDayCheckIn() {
        return dayCheckIn;
    }

    public void setDayCheckIn(LocalDate dayCheckIn) {
        this.dayCheckIn = dayCheckIn;
    }

    public LocalDate getDayCheckOut() {
        return dayCheckOut;
    }

    public void setDayCheckOut(LocalDate dayCheckOut) {
        this.dayCheckOut = dayCheckOut;
    }
    

    public Booking(List<String> policies) {
        this.policies = policies;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Hotel getBookedHotel() {
        return bookedHotel;
    }

    public void setBookedHotel(Hotel bookedHotel) {
        this.bookedHotel = bookedHotel;
    }

    public Tour getBookedTour() {
        return bookedTour;
    }

    public void setBookedTour(Tour bookedTour) {
        this.bookedTour = bookedTour;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<String> getCustomers() {
        return customers;
    }

    public void setCustomers(List<String> customers) {
        this.customers = customers;
    }

    public List<String> getPolicies() {
        return policies;
    }

    public void setPolicies(List<String> policies) {
        this.policies = policies;
    }

    private double calculateTotalPrice() {
        // You can implement your own logic to calculate the total price based on the booked hotel, tour, and number of guests.
        // For simplicity, let's assume a basic calculation here.
        return (bookedHotel.getPricing() + bookedTour.getPrice()) * numberOfGuests;
    }

    @Override
    public String toString() {
        return "Booking{"
                + "bookingID='" + bookingID + '\''
                + ", customerName='" + customerName + '\''
                + ", contactNumber='" + contactNumber + '\''
                + ", bookedHotel=" + bookedHotel
                + ", bookedTour=" + bookedTour
                + ", numberOfGuests=" + numberOfGuests
                + ", totalPrice=" + totalPrice
                + ", customers=" + customers
                + ", dayBooking=" + dayBooking
                + ", dayCheckIn=" + dayCheckIn
                + ", dayCheckOut=" + dayCheckOut
                + '}';
    }

}
