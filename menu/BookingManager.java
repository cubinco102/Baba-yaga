/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import model.Booking;
import model.Hotel;
import model.Tour;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author huyqu
 */
public class BookingManager {

    private List<Booking> bookings;
    private List<Tour> tours;
    private List<Hotel> hotels;
    private static final String FILE_NAME = "Bookings.dat";

    public BookingManager(List<Tour> tours, List<Hotel> hotels) {
        this.hotels = hotels;
        this.tours = tours;
        this.loadBookingFromFile();

        // Ensure that bookings, hotels, and tours are initialized
        if (bookings == null) {
            this.bookings = new ArrayList<>();

        }
    }

// Function to create a new Booking
    public void createNewBooking(String bookingID, String customerName, int contactNumber, String hotelID, String tourID, int numberOfGuests, List<String> customers,
            LocalDate dayBooking, LocalDate dayCheckIn, LocalDate dayCheckOut) {
        // confirm Hotel and tour available for create booking
        if (hotels.isEmpty() || tours.isEmpty()) {
            System.out.println("Hotels or tours list is empty. Cannot create booking.");
            return;
        }

        // Validate input data
        if (bookingID == null || customerName == null || hotelID == null || tourID == null || customers == null) {
            System.out.println("Incomplete input data. Booking not created.");
            return;
        }

        // Additional validation logic can be added based on your requirements
        for (Booking existingBooking : bookings) {
            if (existingBooking.getBookingID().equals(bookingID)) {
                System.out.println("Booking with the same ID already exists. Please provide a unique ID.");
                return;
            }
        }

        // Find the corresponding hotel and tour
        Hotel bookedHotel = null;
        for (Hotel hotel : hotels) {
            if (hotel.getHotelID().equals(hotelID)) {
                bookedHotel = hotel;
                break;
            }
        }

        // Check if the hotel and tour exist
        if (bookedHotel == null) {
            System.out.println("Invalid hotel! Booking not created.");
            return;
        }

        Tour bookedTour = null;
        for (Tour tour : tours) {
            if (tour.getTourID().equals(tourID)) {
                bookedTour = tour;
                break;
            }
        }

        if (bookedTour == null) {
            System.out.println("Invalid tour ID! Booking not created.");
            return;
        }

        // Check availability
        if (bookedHotel.getRoomAvailable() < numberOfGuests) {
            System.out.println("Not enough rooms available. Booking not created.");
            return;
        }

        // Create a new booking
        Booking newBooking = new Booking(bookingID, customerName, contactNumber, bookedHotel, bookedTour, numberOfGuests, customers, dayBooking, dayCheckIn, dayCheckOut);

        // Update availability
        bookedHotel.setRoomAvailable(bookedHotel.getRoomAvailable() - numberOfGuests);

        // Add the new booking to the catalog
        bookings.add(newBooking);

        System.out.println("New booking created successfully!");
        saveBookingToFile();
    }

    public void createNewBookings() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("Enter Booking Details:");

        System.out.print("Enter Booking ID: ");
        String bookingID = scanner.nextLine();

        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter Contact Number: ");
        int contactNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter Hotel ID: ");
        String hotelID = scanner.nextLine();

        System.out.print("Enter Tour ID: ");
        String tourID = scanner.nextLine();

        System.out.print("Number of Guests: ");
        int numberOfGuests = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter customer names (<,>-separated): ");
        String customersInput = scanner.nextLine();
        List<String> customers = parseCustomerNames(customersInput);

        
        System.out.print("Enter day of booking (DD-MM-YYYY): ");
        LocalDate dayBooking = LocalDate.parse(scanner.nextLine(), formatter);
        
        System.out.print("Enter day of check-in (DD-MM-YYYY): ");
        LocalDate dayCheckIn = LocalDate.parse(scanner.nextLine(), formatter);
        
        System.out.print("Enter day of check-out (DD-MM-YYYY): ");
        LocalDate dayCheckOut = LocalDate.parse(scanner.nextLine(), formatter);

        // Call the createNewBooking method with the user input
        createNewBooking(bookingID, customerName, contactNumber, hotelID, tourID, numberOfGuests, customers, dayBooking, dayCheckIn, dayCheckOut);
    }

    private List<String> parseCustomerNames(String input) {
        String[] customerArray = input.split(",");
        List<String> customers = new ArrayList<>();

        for (String customer : customerArray) {
            customers.add(customer.trim());
        }

        return customers;
    }

    public void showBookings() {
        System.out.println("List of Bookings:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Booking booking : bookings) {
            System.out.println("Booking ID: " + booking.getBookingID());
            System.out.println("Customer Name: " + booking.getCustomerName());
            System.out.println("Contact Number: " + booking.getContactNumber());
            System.out.println("Booked Hotel: " + booking.getBookedHotel().getName());
            System.out.println("Booked Tour: " + booking.getBookedTour().getName());
            System.out.println("Number of Guests: " + booking.getNumberOfGuests());
            System.out.println("Day of Booking: " +formatter.format(booking.getDayBooking()));
            System.out.println("Day of CheckIn: " + formatter.format(booking.getDayCheckIn()));
            System.out.println("Day of CheckOut: " + formatter.format(booking.getDayCheckOut()));
            System.out.println("Total Price: " + booking.getTotalPrice());
            System.out.println("Customers: " + booking.getCustomers());
            System.out.println("------------------------");
        }

        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
        }
        loadBookingFromFile();
    }

    public void modifyBookingInformation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Booking ID to modify information: ");
        String bookingID = scanner.nextLine();

        Booking bookingToUpdate = findBookingByID(bookingID);

        if (bookingToUpdate != null) {
            showBookingInformation(bookingToUpdate);
            inputNewBookingInformation(bookingToUpdate);

            System.out.println("Booking information modified successfully!");
            saveBookingToFile(); // Save changes to file or database
        } else {
            System.out.println("Booking with ID " + bookingID + " not found.");
        }
        saveBookingToFile();
    }

    // ham show booking
    private void showBookingInformation(Booking booking) {
        System.out.println("Current Booking Information:");
        System.out.println("Booking ID: " + booking.getBookingID());
        System.out.println("Customer Name: " + booking.getCustomerName());
        System.out.println("Contact Number: " + booking.getContactNumber());
        System.out.println("Booked Hotel: " + booking.getBookedHotel().getName());
        System.out.println("Booked Tour: " + booking.getBookedTour().getName());
        System.out.println("Number of Guests: " + booking.getNumberOfGuests());
        System.out.println("Day of Booking: " + booking.getDayBooking());
        System.out.println("Day of CheckIn: " + booking.getDayCheckIn());
        System.out.println("Day of CheckOut: " + booking.getDayCheckOut());

    }

    // ham nhap new booking
    private void inputNewBookingInformation(Booking booking) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        System.out.println("\nEnter New Booking Information:");

        System.out.print("Customer Name: ");
        String newCustomerName = scanner.nextLine();
        booking.setCustomerName(newCustomerName);

        System.out.print("Contact Number: ");
        int newContactNumber = scanner.nextInt();
        booking.setContactNumber(newContactNumber);
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter New Hotel ID: ");
        String newHotelID = scanner.nextLine();
        Hotel newHotel = findHotelByID(newHotelID);

        if (newHotel != null) {
            booking.setBookedHotel(newHotel);
            System.out.println("New Hotel set successfully.");
        } else {
            System.out.println("Hotel with ID " + newHotelID + " not found.");
        }

        System.out.print("Enter New Tour ID: ");
        String newTourID = scanner.nextLine();
        Tour newTour = findTourByID(newTourID);

        if (newTour != null) {
            booking.setBookedTour(newTour);
            System.out.println("New Tour set successfully.");
        } else {
            System.out.println("Tour with ID " + newTourID + " not found.");
        }

        
        // Use findHotelByID(newHotelID) to find the new hotel and set it to booking.setBookedHotel(newHotel);

        System.out.print("Number of Guests: ");
        int newNumberOfGuests = scanner.nextInt();
        booking.setNumberOfGuests(newNumberOfGuests);
        scanner.nextLine(); // Consume the newline character
        
        System.out.print("Enter day of booking (DD-MM-YYYY): ");
        LocalDate newDayBooking = LocalDate.parse(scanner.nextLine(),formatter);
        booking.setDayBooking(newDayBooking);
         
        System.out.println("Enter New day Check In(DD-MM-YYYY)");
        LocalDate newDayCheckIn = LocalDate.parse(scanner.nextLine(),formatter);
        booking.setDayBooking(newDayCheckIn);
         
        System.out.println("Enter New day Check Out(DD-MM-YYYY)");
        LocalDate newDayCheckOut = LocalDate.parse(scanner.nextLine(),formatter);
        booking.setDayCheckOut(newDayCheckOut);
        saveBookingToFile();
        
    }

    public Hotel findHotelByID(String hotelID) {
        for (Hotel hotel : hotels) {
            if (hotel.getHotelID().equals(hotelID)) {
                return hotel;
            }
        }
        return null; // Hotel with specified ID not found
    }

    public Tour findTourByID(String tourID) {
        for (Tour tour : tours) {
            if (tour.getTourID().equals(tourID)) {
                return tour;
            }
        }
        return null; // Tour with specified ID not found
    }

    private Booking findBookingByID(String bookingID) {
        for (Booking booking : bookings) {
            if (booking.getBookingID().equals(bookingID)) {
                return booking;
            }
        }
        return null;
    }

    public void removeBooking() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Booking ID to remove: ");
        String bookingIDToRemove = scanner.nextLine();

        Iterator<Booking> iterator = bookings.iterator();

        while (iterator.hasNext()) {
            Booking booking = iterator.next();

            if (booking.getBookingID().equals(bookingIDToRemove)) {
                // Check policies before removing
                if (isRemovalAllowed(booking)) {
                    // Adjust availability if necessary
                    adjustAvailabilityOnRemoval(booking);

                    // Remove the booking
                    iterator.remove();
                    System.out.println("Booking removed successfully!");
                    saveBookingToFile(); // Save changes to file or database
                    return;
                } else {
                    System.out.println("Removal not allowed by policies.");
                    return;
                }

            }
            saveBookingToFile();
        }

        System.out.println("Booking with ID " + bookingIDToRemove + " not found.");
    }

    private boolean isRemovalAllowed(Booking booking) {
        // Add your removal policies here
        // For example, you might have policies based on time or other conditions
        return true;
    }

    private void adjustAvailabilityOnRemoval(Booking booking) {
        // Adjust availability based on your logic
        // For example, add back the booked rooms for a hotel
        Hotel bookedHotel = booking.getBookedHotel();
        int numberOfGuests = booking.getNumberOfGuests();
        bookedHotel.setRoomAvailable(bookedHotel.getRoomAvailable() + numberOfGuests);
    }

    public void bookingManagementMenu(BookingManager bookingManager) {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("+-----+----------------------------------------+");
            System.out.println("||    |      Booking Management Menu          ||");
            System.out.println("+-----+----------------------------------------+");
            System.out.println("|| 1  | Create New Bookings                   ||");
            System.out.println("|| 2  | Show Bookings                         ||");
            System.out.println("|| 3  | Modify Booking Information            ||");
            System.out.println("|| 4  | Remove Booking                        ||");
            System.out.println("|| 5  | Return main Menu                      ||");
            System.out.println("+-----+----------------------------------------+");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookingManager.createNewBookings();
                    break;
                case 2:
                    bookingManager.showBookings();
                    break;
                case 3:
                    bookingManager.modifyBookingInformation();
                    break;
                case 4:
                    bookingManager.removeBooking();
                    break;
                case 5:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        } while (choice != 5);
    }

    private void saveBookingToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(bookings);
            oos.close();
            System.out.println("Booking tour saved successfully to file.");
        } catch (IOException e) {
            System.out.println("Error saving Booking tour to file: " + e.getMessage());
        }

    }

    private void loadBookingFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            bookings = (List<Booking>) ois.readObject();
            ois.close();
            System.out.println("Booking loaded from file successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading Booking tour from file: " + e.getMessage());
        }

    }

}
