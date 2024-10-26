/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import model.Hotel;

/**
 *
 * @author huyqu
 */
public class HotelManager {

    private List<Hotel> hotels;
    private static String FILE_NAME = "Hotels.dat";

    public HotelManager() {
        this.loadHotelFromFile();
        if (hotels == null) {
            this.hotels = new ArrayList<>();

        }
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void addNewHotel(String hotelID, String name, String location, int roomAvailable, List<String> amenities, double pricing) {
        // Buộc người dùng phải nhập dữ liệu đầy đủ
        if (hotelID == null || name == null || location == null || amenities == null) {
            System.out.println("Invalid input. Please provide valid data.");
            return;
        }

        // check hotelID đã tồn tại
        for (Hotel existingHotel : hotels) {
            if (existingHotel.getHotelID().equals(hotelID)) {
                System.out.println("Hotel with the same ID already exists. Please provide a unique ID.");
                return;
            }
        }

        Hotel newHotel = new Hotel(hotelID, name, location, roomAvailable, amenities, pricing);

        hotels.add(newHotel);

        System.out.println("New hotel added successfully.");
        saveHotelToFile();
    }

    public void addHotels() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter details for the new hotel:");

        System.out.print("Enter Hotel ID (include H + 2 number): ");
        String hotelID = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        System.out.print("Enter Room Available: ");
        int roomAvailable = scanner.nextInt();

        System.out.print("Enter Amenities (comma-separated): ");
        scanner.nextLine(); // Consume the newline character left by nextInt()
        String amenitiesInput = scanner.nextLine();
        List<String> amenities = new ArrayList<>(Arrays.asList(amenitiesInput.split(",")));

        System.out.print("Enter Pricing Per Night: ");
        double pricing = scanner.nextDouble();

        // Call the addNewHotel method with the user-inputted data
        addNewHotel(hotelID, name, location, roomAvailable, amenities, pricing);
    }

    // phương thức tìm kiếm bằng vị trí
    public List<Hotel> searchHotelsByLocation(String partialLocation) {
        return hotels.stream()
                .filter(hotel -> partialLocation == null || hotel.getLocation().toLowerCase().contains(partialLocation.toLowerCase()))
                .collect(Collectors.toList());
    }

    //phương thức tìm kiếm bằng amenities
    public List<Hotel> searchHotelsByAmenities(List<String> searchAmenities) {
        return hotels.stream()
                .filter(hotel -> searchAmenities == null || hotel.getAmenities().containsAll(searchAmenities))
                .collect(Collectors.toList());

    }

    //phương thức tìm kiếm bằng roomAvailable
    public List<Hotel> searchHotelsByRoomAvailability(int minRoomAvailable) {
        return hotels.stream()
                .filter(hotel -> hotel.getRoomAvailable() >= minRoomAvailable)
                .collect(Collectors.toList());
    }

    public void searchingHotelMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nSearching Menu:");
            System.out.println("1. Search by Location");
            System.out.println("2. Search by Amenities");
            System.out.println("3. Search by Room Availability");
            System.out.println("4. Back to Hotel Management Menu  ");
            System.out.print("Enter your choice (1-4): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    searchByLocationSubMenu();
                    break;
                case 2:
                    searchByAmenitiesSubMenu();
                    break;
                case 3:
                    searchByRoomAvailabilitySubMenu();
                    break;
                case 4:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while (choice != 4);
    }

//  method for searching by location
    private void searchByLocationSubMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter partial location to search: ");
        String partialLocation = scanner.next();

        // gọi hàm seachHotelByLocation
        List<Hotel> searchResults = searchHotelsByLocation(partialLocation);

        // Display search results
        System.out.println("Search Results:");
        for (Hotel result : searchResults) {
            System.out.println(result);
        }
        loadHotelFromFile();
    }
//  method for searching by Amenities

    private void searchByAmenitiesSubMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter amenities to search (comma-separated): ");
        String amenitiesInput = scanner.next();
        List<String> searchAmenities = Arrays.asList(amenitiesInput.split(","));

        //gọi hàm search by amenities
        List<Hotel> searchResults = searchHotelsByAmenities(searchAmenities);

        // Display search results
        System.out.println("Search Results:");
        for (Hotel result : searchResults) {
            System.out.println(result);
        }
        loadHotelFromFile();
    }
//  method for searching by RoomAvailability

    private void searchByRoomAvailabilitySubMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the minimum Room Availability to search: ");
        int minRoomAvailable = scanner.nextInt();

        // gọi hàm search by room availability
        List<Hotel> searchResults = searchHotelsByRoomAvailability(minRoomAvailable);

        // Display search results
        System.out.println("Search Results:");
        for (Hotel result : searchResults) {
            System.out.println(result);
        }
        loadHotelFromFile();
    }

    public void updateHotelMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nUpdate Operations Sub-Menu:");
            System.out.println("1. Update Room Availability");
            System.out.println("2. Update Pricing");
            System.out.println("3. Edit Amenities");
            System.out.println("4. Back to Hotel Management Menu  ");
            System.out.print("Enter your choice (1-4): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    updateRoomAvailability();
                    break;
                case 2:
                    updatePricing();
                    break;
                case 3:
                    editAmenities();
                    break;
                case 4:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while (choice != 4);
    }

    private void updateRoomAvailability() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Hotel ID you want to update Room Availability for: ");
        String hotelID = scanner.nextLine();

        boolean hotelFound = false;

        for (Hotel hotel : hotels) {
            if (hotel.getHotelID().equals(hotelID)) {
                hotelFound = true;
                System.out.println("Updating RoomAvailability for " + hotel.getName());

                System.out.print("Enter the new RoomAvailability for " + hotel.getName() + ":");
//                double newPrice = scanner.nextDouble();
//                hotel.setPricing(newPrice);
                int newRoomAvailability = scanner.nextInt();
                hotel.setRoomAvailable(newRoomAvailability);

                System.out.println("Hotel RoomAvailability updated successfully!");
                break;
            }
        }

        if (!hotelFound) {
            System.out.println("Hotel not found. Please enter a valid Hotel ID.");
        }
        saveHotelToFile();
    }

    private void updatePricing() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Hotel ID you want to update pricing for: ");
        String hotelID = scanner.nextLine();

        boolean hotelFound = false;

        for (Hotel hotel : hotels) {
            if (hotel.getHotelID().equals(hotelID)) {
                hotelFound = true;
                System.out.println("Updating Pricing for " + hotel.getName());

                System.out.print("Enter the new price for " + hotel.getName() + ": $");
                double newPrice = scanner.nextDouble();
                hotel.setPricing(newPrice);

                System.out.println("Hotel pricing updated successfully!");
                break;
            }
        }

        if (!hotelFound) {
            System.out.println("Hotel not found. Please enter a valid Hotel ID.");
        }
        saveHotelToFile();
    }

    private void editAmenities() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Tour ID you want to update Amenities for: ");
        String hotelID = scanner.nextLine();

        boolean hotelFound = false;

        for (Hotel hotel : hotels) {
            if (hotel.getHotelID().equals(hotelID)) {
                hotelFound = true;
                System.out.println("Updating amenities for " + hotel.getName());

                System.out.println("Enter new amenities(separated by (,)): ");
                String newAmenitiesInput = scanner.nextLine();
                List<String> newAmenities = Arrays.asList(newAmenitiesInput.split(","));
                hotel.setAmenities(newAmenities);

                System.out.println("Amenities updated successfully!");
                break;  // Exit the loop once the tour is found and inclusions/exclusions are updated
            }
        }

        if (!hotelFound) {
            System.out.println("Hotel not found. Please enter a valid Tour ID.");
        }
        saveHotelToFile();
    }

    public void showHotels() {
        if (hotels.isEmpty()) {
            System.out.println("No hotels available in the catalog.");
            return;
        }

        System.out.println("Hotel Catalog:");

        for (Hotel hotel : hotels) {
            System.out.println("Hotel ID: " + hotel.getHotelID());
            System.out.println("Hotel Name: " + hotel.getName());
            System.out.println("Location: " + hotel.getLocation());
            System.out.println("Room Available: " + hotel.getRoomAvailable());
            System.out.println("Pricing: VND" + hotel.getPricing());

            System.out.println("Amenities:");
            for (String amenity : hotel.getAmenities()) {
                System.out.println("- " + amenity);
            }

            System.out.println("------------------------------");
        }
    }

    private String getHotelIDToDelete() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Hotel ID you want to delete (or type 'x' to cancel): ");
        return scanner.nextLine();
    }

    private void deleteHotel(String hotelIDToDelete) {
        boolean hotelFound = false;

        Iterator<Hotel> iterator = hotels.iterator();
        while (iterator.hasNext()) {
            Hotel hotel = iterator.next();
            if (hotel.getHotelID().equals(hotelIDToDelete)) {
                hotelFound = true;
                System.out.println("Hotel Information:\n" + hotel);

                if (askUserConfirmationToDelete(hotel.getName())) {
                    iterator.remove();
                    System.out.println("Hotel deleted successfully!");
                } else {
                    System.out.println("Deletion canceled.");
                }
                break;
            }
        }

        if (!hotelFound) {
            System.out.println("Hotel not found. Please enter a valid Hotel ID.");
        }
        saveHotelToFile();
    }

    public void deleteHotels() {
        String hotelIDToDelete = getHotelIDToDelete();

        if (!hotelIDToDelete.equalsIgnoreCase("x")) {
            deleteHotel(hotelIDToDelete);
            saveHotelToFile();
        }
    }

    private boolean askUserConfirmationToDelete(String hotelName) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Are you sure you want to delete " + hotelName + "? (y/n): ");
        String confirmation = scanner.nextLine().toLowerCase();

        return confirmation.equals("y");
    }

    public void hotelManagementMenu(HotelManager hotelManager) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("+-----+----------------------------------------+");
            System.out.println("||    |      Hotel Management Menu            ||");
            System.out.println("+-----+----------------------------------------+");
            System.out.println("|| 1  | Add new hotel                         ||");
            System.out.println("|| 2  | Search Hotel                          ||");
            System.out.println("|| 3  | Update Hotel                          ||");
            System.out.println("|| 4  | Show Hotel                            ||");
            System.out.println("|| 5  | Delete Hotel                          ||");
            System.out.println("|| 6  | Return main Menu                      ||");
            System.out.println("+-----+----------------------------------------+");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    hotelManager.addHotels();
                    break;
                case 2:
                    hotelManager.searchingHotelMenu();
                    break;
                case 3:
                    hotelManager.updateHotelMenu();
                    break;
                case 4:
                    hotelManager.showHotels();
                    break;
                case 5:
                    hotelManager.deleteHotels();
                    break;
                case 6:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while (choice != 6);
    }

    private void saveHotelToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(hotels);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error saving hotels to file: " + e.getMessage());
        }

    }

    private void loadHotelFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            hotels = (List<Hotel>) ois.readObject();
            ois.close();
            System.out.println("Hotel loaded from file successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading Hotels from file: " + e.getMessage());
        }

    }
}
