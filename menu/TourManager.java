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
import model.Tour;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author huyqu
 */
public class TourManager {

    private List<Tour> tours;
    private static final String FILE_NAME = "Tours.dat";

    public TourManager() {
        this.loadTourFromFile();
        if (tours == null) {
            this.tours = new ArrayList<>();
        }
    }

    public List<Tour> getTours() {
        return tours;
    }

    // Function to create a new toure
    public void createNewTour(String tourID, String name, String destination, int duration,
            double price, String description, List<String> inclusions,
            List<String> exclusions) {
        // Validate input data
        if (tourID == null || name == null || destination == null || description == null
                || inclusions == null || exclusions == null) {
            System.out.println("Incomplete input data. Tour not created.");
            return;
        }

        // Additional validation logic can be added based on your requirements
        for (Tour existingTour : tours) {
            if (existingTour.getTourID().equals(tourID)) {
                System.out.println("Tour with the same ID already exists. Please provide a unique ID.");
                return;
            }
        }
        // Create a new tour
        Tour newTours = new Tour(tourID, name, destination, duration, price, description, inclusions, exclusions);

        // Add the new tour to the catalog
        tours.add(newTours);

        System.out.println("New tour created successfully!");
        saveTourToFile();
    }

    public void createNewTours() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Tour Details:");

        System.out.print("Enter Tour ID(include T + 2 number): ");
        String tourID = scanner.nextLine();

        System.out.print("Enter Tour Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();

        System.out.print("Enter Duration (in days): ");
        int duration = scanner.nextInt();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.println("Enter Inclusions (comma-separated): ");
        List<String> inclusions = Arrays.asList(scanner.nextLine().split(","));

        System.out.println("Enter Exclusions (comma-separated): ");
        List<String> exclusions = Arrays.asList(scanner.nextLine().split(","));

        // Call the existing createNewTour method with the gathered input
        createNewTour(tourID, name, destination, duration, price, description, inclusions, exclusions);

    }

    public List<Tour> searchToursByDestination(String partialDestination) {
        return tours.stream()
                .filter(tour -> partialDestination == null || tour.getDetination().toLowerCase().contains(partialDestination.toLowerCase()))
                .collect(Collectors.toList());
    }

    //phương thức tìm kiếm bằng amenities
    public List<Tour> searchToursByDuration(int minDuration, int maxDuration) {
        return tours.stream()
                .filter(tour -> tour.getDuration() >= minDuration && tour.getDuration() <= maxDuration)
                .collect(Collectors.toList());
    }

    //phương thức tìm kiếm bằng roomAvailable
    public List<Tour> searchToursByPrice(double minPrice, double maxPrice) {
        return tours.stream()
                .filter(tour -> tour.getPrice() >= minPrice && tour.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public void searchingToursMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nSearching Menu:");
            System.out.println("1. Search by Destination");
            System.out.println("2. Search by Duration");
            System.out.println("3. Search by Price");
            System.out.println("4. Back to Tour Management Menu  ");
            System.out.print("Enter your choice (1-4): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    searchTourByDestination();
                    break;
                case 2:
                    searchTourByDuration();
                    break;
                case 3:
                    searchTourByPrice();
                    break;
                case 4:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while (choice != 4);
    }
    // method for searchTourByDestination

    private void searchTourByDestination() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter partial destination to search: ");
        String partialDestination = scanner.next();

        // gọi hàm searchToursByDestination
        List<Tour> searchResults = searchToursByDestination(partialDestination);

        // Display search results
        System.out.println("Search Results:");
        for (Tour result : searchResults) {
            System.out.println(result);
        }
        loadTourFromFile();
    }
    // method for searchTourByDuration

    private void searchTourByDuration() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter min duration to search: ");
        int minDuration = scanner.nextInt();

        System.out.print("Enter max duration to search: ");
        int maxDuration = scanner.nextInt();

        List<Tour> searchedTours = searchToursByDuration(minDuration, maxDuration);

        System.out.println("Search Results:");
        if (searchedTours.isEmpty()) {
            System.out.println("No tours found within the specified duration range.");
        } else {
            for (Tour tour : searchedTours) {
                System.out.println(tour.getName() + " - Duration: " + tour.getDuration() + " days");
            }
            loadTourFromFile();
        }
    }

    // method searchByPrice
    private void searchTourByPrice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter minimum price want to search: ");
        double minPrice = scanner.nextDouble();

        System.out.print("Enter minimum price want to search: ");
        double maxPrice = scanner.nextDouble();

        // call method searchToursByPrice
        List<Tour> searchedTours = searchToursByPrice(minPrice, maxPrice);

        System.out.println("Search Results:");
        if (searchedTours.isEmpty()) {
            System.out.println("No tours found within the specified pricen range.");
        } else {
            for (Tour tour : searchedTours) {
                System.out.println(tour.getName() + " - Price: $" + tour.getPrice());
            }
            loadTourFromFile();
        }
    }

    public void updateTourMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nUpdate Operations Sub-Menu:");
            System.out.println("Edit Tour Menu:");
            System.out.println("1. Update Details");
            System.out.println("2. Update Pricing");
            System.out.println("3. Update Inclusions/Exclusions");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice (1-4): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    updateDetails();
                    break;
                case 2:
                    updateTourPricing();
                    break;
                case 3:
                    updateInclusionsExclusions();
                    break;
                case 4:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while (choice != 4);
    }

    private void updateDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Tour ID you want to update: ");
        String tourID = scanner.nextLine();

        boolean tourFound = false;

        for (Tour tour : tours) {
            if (tour.getTourID().equals(tourID)) {
                tourFound = true;
                System.out.println("Modifying Tour Details for " + tour.getName());

                System.out.print("Enter new destination: ");
                String newDestination = scanner.nextLine();
                tour.setDetination(newDestination);

                System.out.print("Enter new duration: ");
                int newDuration = scanner.nextInt();
                tour.setDuration(newDuration);

                scanner.nextLine();

                System.out.print("Enter new description: ");
                String newDescription = scanner.nextLine();

                tour.setDescription(newDescription);

                // You can continue this pattern for other attributes like name, inclusions, exclusions, etc.
                System.out.println("Tour details modified successfully!");
                break;  // Exit the loop once the tour is found and modified
            }
        }

        if (!tourFound) {
            System.out.println("Tour not found. Please enter a valid Tour ID.");
        }
        saveTourToFile();
    }

    private void updateTourPricing() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Tour ID you want to update pricing for: ");
        String tourID = scanner.nextLine();

        boolean tourFound = false;

        for (Tour tour : tours) {
            if (tour.getTourID().equals(tourID)) {
                tourFound = true;
                System.out.println("Updating Pricing for " + tour.getName());

                System.out.print("Enter the new price for " + tour.getName() + ": $");
                double newPrice = scanner.nextDouble();
                tour.setPrice(newPrice);

                System.out.println("Tour pricing updated successfully!");
                break;  // 
            }
        }

        if (!tourFound) {
            System.out.println("Tour not found. Please enter a valid Tour ID.");
        }
        saveTourToFile();
        
    }

    private void updateInclusionsExclusions() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Tour ID you want to update inclusions/exclusions for: ");
        String tourID = scanner.nextLine();

        boolean tourFound = false;

        for (Tour tour : tours) {
            if (tour.getTourID().equals(tourID)) {
                tourFound = true;
                System.out.println("Updating Inclusions/Exclusions for " + tour.getName());

                System.out.println("Enter new inclusions (separated by commas): ");
                String newInclusionsInput = scanner.nextLine();
                List<String> newInclusions = Arrays.asList(newInclusionsInput.split(","));

                System.out.println("Enter new exclusions (separated by commas): ");
                String newExclusionsInput = scanner.nextLine();
                List<String> newExclusions = Arrays.asList(newExclusionsInput.split(","));

                tour.setInclusions(newInclusions);
                tour.setExclusions(newExclusions);

                System.out.println("Inclusions/Exclusions updated successfully!");
                break;  // Exit the loop once the tour is found and inclusions/exclusions are updated
            }
        }

        if (!tourFound) {
            System.out.println("Tour not found. Please enter a valid Tour ID.");
        }
        saveTourToFile();
    }

    public void showTours() {
        if (tours.isEmpty()) {
            System.out.println("No tours available in the catalog.");
            return;
        }

        System.out.println("Tour Catalog:");

        for (Tour tour : tours) {
            System.out.println("Tour ID: " + tour.getTourID());
            System.out.println("Tour Name: " + tour.getName());
            System.out.println("Destination: " + tour.getDetination());
            System.out.println("Duration: " + tour.getDuration() + " days");
            System.out.println("Price: VND" + tour.getPrice());
            System.out.println("Description: " + tour.getDescription());

            System.out.println("Inclusions:");
            for (String inclusion : tour.getInclusions()) {
                System.out.println("- " + inclusion);
            }

            System.out.println("Exclusions:");
            for (String exclusion : tour.getExclusions()) {
                System.out.println("- " + exclusion);
            }

            System.out.println("------------------------------");
        }
        loadTourFromFile();
    }

    public void tourManagementMenu(TourManager tourManager) {
        Scanner scanner = new Scanner(System.in);

        int choice;

        do {
            System.out.println("+-----+----------------------------------------+");
            System.out.println("||    |       Tour Management Menu            ||");
            System.out.println("+-----+----------------------------------------+");
            System.out.println("|| 1  | Create new Tour                       ||");
            System.out.println("|| 2  | Search Tour                           ||");
            System.out.println("|| 3  | Update Hotel                          ||");
            System.out.println("|| 4  | Remove Tour                           ||");
            System.out.println("|| 5  | Show Tour                             ||");
            System.out.println("|| 6  | Return main Menu                      ||");
            System.out.println("+-----+----------------------------------------+");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    tourManager.createNewTours();
                    break;
                case 2:
                    tourManager.searchingToursMenu();
                    break;
                case 3:
                    tourManager.updateTourMenu();
                    break;
                case 4:
                    tourManager.removeTourFromCatalog();
                    break;
                case 5:
                    tourManager.showTours();
                    break;
                case 6:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        } while (choice != 6);
    }

    // method save to file    
    private void saveTourToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tours);
            oos.close();
            System.out.println("Tour saved successfully to file.");
        } catch (IOException e) {
            System.out.println("Error saving Tour to file: " + e.getMessage());
        }

    }
    // method load from file

    private void loadTourFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            tours = (List<Tour>) ois.readObject();
            ois.close();
            System.out.println("Tours loaded from file successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading Tours from file: " + e.getMessage());
        }

    }

    public void removeTourFromCatalog() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Tour ID you want to remove: ");
        String tourID = scanner.nextLine();

        boolean tourFound = false;

        Iterator<Tour> iterator = tours.iterator();
        while (iterator.hasNext()) {
            Tour tour = iterator.next();
            if (tour.getTourID().equals(tourID)) {
                tourFound = true;

                System.out.println("Removing Tour: " + tour.getName());
                System.out.print("Are you sure you want to remove this tour? (Enter 'yes' to confirm): ");
                String confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("yes")) {
                    iterator.remove(); // Remove the tour from the catalog
                    System.out.println("Tour removed from catalog successfully!");
                } else {
                    System.out.println("Tour removal canceled.");
                }
                break;
            }
        }

        if (!tourFound) {
            System.out.println("Tour not found. Please enter a valid Tour ID.");
        }
        saveTourToFile();
    }
}
