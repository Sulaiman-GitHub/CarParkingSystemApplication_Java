import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ParkingSystem {

    static int totalSlots, availableSlots;
    static ArrayList<Car> parkedCars = new ArrayList<Car>();

    public static void main(String[] args) {

        // Ask user for total slots using a dialog
        while (true) {
            String input = JOptionPane.showInputDialog(null, "Enter the total number of parking slots:", "Parking System - Setup", JOptionPane.QUESTION_MESSAGE);
            if (input == null) { // user cancelled
                System.exit(0);
            }
            input = input.trim();
            try {
                totalSlots = Integer.parseInt(input);
                if (totalSlots <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number.", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid integer.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            }
        }
        availableSlots = totalSlots;

        // Main loop - use dialogs for menu selection
        while (true) {
            String menu = "What would you like to do?\n1. Park a car\n2. Remove a car\n3. View parked cars\n4. Exit";
            String choiceStr = JOptionPane.showInputDialog(null, menu, "Parking System - Menu", JOptionPane.QUESTION_MESSAGE);
            if (choiceStr == null) { // treat cancel as exit
                System.exit(0);
            }
            choiceStr = choiceStr.trim();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid choice. Please enter a number between 1 and 4.", "Invalid input", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            switch (choice) {
                case 1:
                    parkCar();
                    break;
                case 2:
                    removeCar();
                    break;
                case 3:
                    viewParkedCars();
                    break;
                case 4:
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void parkCar() {
        if (availableSlots == 0) {
            JOptionPane.showMessageDialog(null, "Sorry, there are no available parking slots.", "Parking Full", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String licensePlate = JOptionPane.showInputDialog(null, "Enter the license plate number of the car:", "Park Car", JOptionPane.QUESTION_MESSAGE);
        if (licensePlate == null) { // cancel
            return;
        }
        licensePlate = licensePlate.trim();
        if (licensePlate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "License plate cannot be empty.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String carName = JOptionPane.showInputDialog(null, "Enter the name of the car (e.g., Toyota Camry):", "Car Name", JOptionPane.QUESTION_MESSAGE);
        if (carName == null) { // cancel
            return;
        }
        carName = carName.trim();
        if (carName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Car name cannot be empty.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        parkedCars.add(new Car(licensePlate, carName));
        availableSlots--;
        JOptionPane.showMessageDialog(null, "Car parked successfully. Available slots: " + availableSlots, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void removeCar() {
        if (availableSlots == totalSlots) {
            JOptionPane.showMessageDialog(null, "There are no parked cars.", "No Cars", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String licensePlate = JOptionPane.showInputDialog(null, "Enter the license plate number of the car to be removed:", "Remove Car", JOptionPane.QUESTION_MESSAGE);
        if (licensePlate == null) { // cancel
            return;
        }
        licensePlate = licensePlate.trim();
        Car found = null;
        for (Car c : parkedCars) {
            if (c.licensePlate.equalsIgnoreCase(licensePlate)) {
                found = c;
                break;
            }
        }
        if (found != null) {
            parkedCars.remove(found);
            availableSlots++;
            JOptionPane.showMessageDialog(null, "Car removed successfully. Available slots: " + availableSlots, "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "The car is not parked here.", "Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void viewParkedCars() {
        if (availableSlots == totalSlots) {
            JOptionPane.showMessageDialog(null, "There are no parked cars.", "No Cars", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Parked cars (License Plate - Name):\n");
        for (Car c : parkedCars) {
            sb.append(c.licensePlate).append(" - ").append(c.name).append('\n');
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Parked Cars", JOptionPane.INFORMATION_MESSAGE);
    }

    // Simple holder for car data
    static class Car {
        String licensePlate;
        String name;

        Car(String licensePlate, String name) {
            this.licensePlate = licensePlate;
            this.name = name;
        }
    }
}
