/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package executive;

import menu.HotelManager;
import menu.TourManager;
import menu.BookingManager;
import java.util.Scanner;

/**
 *
 * @author huyqu
 */
public class TourManagementSystem {

    public static void main(String[] args) {
        HotelManager hotelManager = new HotelManager();
        TourManager tourManager = new TourManager();
        BookingManager bookingManager = new BookingManager(tourManager.getTours(),hotelManager.getHotels());
        Scanner scanner = new Scanner(System.in);
        

        int choice;

        do {
        System.out.println("+-----+----------------------------------------+");
        System.out.println("||    |               Menu Option             ||");
        System.out.println("+-----+----------------------------------------+");
        System.out.println("|| 1  | Tour Management Menu                  ||");
        System.out.println("|| 2  | Hotel Management Menu                 ||");
        System.out.println("|| 3  | Booking Management Menu               ||");
        System.out.println("|| 4  | Exit                                  ||");
        System.out.println("+-----+----------------------------------------+");
        
        
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    tourManager.tourManagementMenu(tourManager);
                    break;
                case 2:
                    hotelManager.hotelManagementMenu(hotelManager);
                    break;
                case 3:
                    bookingManager.bookingManagementMenu(bookingManager);
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } while (choice != 4);

        scanner.close();
    }

}
