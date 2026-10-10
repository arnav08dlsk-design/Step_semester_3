package oo_design.class_problems;

import java.util.Locale;

/**
 * Vehicle Rental System
 *
 * UML relationships:
 *   Vehicle <|-- Sedan, SUV, Truck      generalization (each category prices itself)
 *   Customer 1 --- 0..* Rental          association
 *   Vehicle  1 --- 0..* Rental          association (only one ACTIVE rental per vehicle at a time)
 *   RentalService ..> Vehicle           depends on the abstraction only, so a new
 *                                       category (e.g. Van) needs no change to the rental logic
 */
public class P1_VehicleRentalSystem {

    static String money(double v) {
        return String.format(Locale.US, "$%.2f", v);
    }

    static class Customer {
        private final String name;

        Customer(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    abstract static class Vehicle {
        private final String name;
        private boolean available = true;

        Vehicle(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        boolean isAvailable() {
            return available;
        }

        void markRented() {
            if (!available) {
                throw new IllegalStateException(name + " is currently unavailable.");
            }
            available = false;
        }

        void markReturned() {
            available = true;
        }

        /** Each vehicle category supplies its own pricing rule. */
        abstract double calculateCharge(int days);
    }

    static class Sedan extends Vehicle {
        Sedan(String name) {
            super(name);
        }

        @Override
        double calculateCharge(int days) {
            return 50.0 * days;
        }
    }

    static class SUV extends Vehicle {
        SUV(String name) {
            super(name);
        }

        @Override
        double calculateCharge(int days) {
            double charge = 80.0 * days;
            if (days >= 5) {
                charge *= 0.90; // 10% off long rentals
            }
            return charge;
        }
    }

    static class Truck extends Vehicle {
        Truck(String name) {
            super(name);
        }

        @Override
        double calculateCharge(int days) {
            return 100.0 * days + 75.0; // flat loading fee
        }
    }

    static class Rental {
        private final Customer customer;
        private final Vehicle vehicle;
        private final int days;
        private final double charge;
        private boolean active = true;

        Rental(Customer customer, Vehicle vehicle, int days, double charge) {
            this.customer = customer;
            this.vehicle = vehicle;
            this.days = days;
            this.charge = charge;
        }

        Customer getCustomer() {
            return customer;
        }

        Vehicle getVehicle() {
            return vehicle;
        }

        int getDays() {
            return days;
        }

        double getCharge() {
            return charge;
        }

        boolean isActive() {
            return active;
        }

        void close() {
            if (!active) {
                throw new IllegalStateException("This rental is already closed.");
            }
            active = false;
            vehicle.markReturned();
        }
    }

    static class RentalService {
        Rental rentVehicle(Customer customer, Vehicle vehicle, int days) {
            if (days <= 0) {
                throw new IllegalArgumentException("Rental duration must be at least 1 day.");
            }
            if (!vehicle.isAvailable()) {
                System.out.println(vehicle.getName() + " is currently unavailable.");
                return null;
            }
            vehicle.markRented();
            Rental rental = new Rental(customer, vehicle, days, vehicle.calculateCharge(days));
            System.out.println(vehicle.getName() + " rented successfully by " + customer.getName()
                    + ". Rental charge: " + money(rental.getCharge()) + ".");
            return rental;
        }

        void returnVehicle(Rental rental) {
            rental.close();
            System.out.println(rental.getVehicle().getName() + " returned by "
                    + rental.getCustomer().getName() + ".");
        }
    }

    public static void main(String[] args) {
        RentalService service = new RentalService();
        Customer c1 = new Customer("Customer 1");
        Customer c2 = new Customer("Customer 2");
        Customer c3 = new Customer("Customer 3");
        Vehicle sedanA = new Sedan("Sedan A");
        Vehicle suvB = new SUV("SUV B");

        Rental r1 = service.rentVehicle(c1, sedanA, 3);
        service.rentVehicle(c2, sedanA, 2);   // rejected: already rented
        service.returnVehicle(r1);
        service.rentVehicle(c3, suvB, 5);
    }
}
