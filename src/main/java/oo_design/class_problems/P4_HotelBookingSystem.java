package oo_design.class_problems;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Hotel Booking System
 *
 * UML relationships:
 *   Room <|-- StandardRoom, DeluxeRoom, Suite   generalization (each category prices itself)
 *   Customer 1 --- 0..* Reservation             association
 *   Room     1 --- 0..* Reservation             association (active ones never overlap)
 *   HotelService ..> Room                       depends on the abstraction only
 *   Check-out day is exclusive, so a stay ending Jan 5 does not clash with one starting Jan 5.
 */
public class P4_HotelBookingSystem {

    static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH);

    static String money(double v) {
        return String.format(Locale.US, "$%.2f", v);
    }

    static String range(LocalDate s, LocalDate e) {
        if (s.getMonth() == e.getMonth() && s.getYear() == e.getYear()) {
            return FMT.format(s) + "-" + e.getDayOfMonth();
        }
        return FMT.format(s) + " - " + FMT.format(e);
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

    abstract static class Room {
        private final int number;
        private final List<Reservation> reservations = new ArrayList<Reservation>();

        Room(int number) {
            this.number = number;
        }

        abstract String getCategory();

        /** Each room category applies its own pricing rule. */
        abstract double calculatePrice(int nights);

        String getLabel() {
            return getCategory() + " " + number;
        }

        boolean isAvailable(LocalDate from, LocalDate to) {
            for (Reservation r : reservations) {
                if (r.isActive() && from.isBefore(r.getCheckOut()) && r.getCheckIn().isBefore(to)) {
                    return false;
                }
            }
            return true;
        }

        void addReservation(Reservation r) {
            reservations.add(r);
        }
    }

    static class StandardRoom extends Room {
        StandardRoom(int number) {
            super(number);
        }

        @Override
        String getCategory() {
            return "Standard Room";
        }

        @Override
        double calculatePrice(int nights) {
            return 100.0 * nights;
        }
    }

    static class DeluxeRoom extends Room {
        DeluxeRoom(int number) {
            super(number);
        }

        @Override
        String getCategory() {
            return "Deluxe Room";
        }

        @Override
        double calculatePrice(int nights) {
            return 150.0 * nights;
        }
    }

    static class Suite extends Room {
        Suite(int number) {
            super(number);
        }

        @Override
        String getCategory() {
            return "Suite";
        }

        @Override
        double calculatePrice(int nights) {
            return 250.0 * nights + 50.0; // flat concierge fee
        }
    }

    static class Reservation {
        private final Customer customer;
        private final Room room;
        private final LocalDate checkIn;
        private final LocalDate checkOut;
        private final double price;
        private boolean active = true;

        Reservation(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut, double price) {
            this.customer = customer;
            this.room = room;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
            this.price = price;
        }

        Customer getCustomer() {
            return customer;
        }

        Room getRoom() {
            return room;
        }

        LocalDate getCheckIn() {
            return checkIn;
        }

        LocalDate getCheckOut() {
            return checkOut;
        }

        double getPrice() {
            return price;
        }

        boolean isActive() {
            return active;
        }

        /** Cancellation closes two days before check-in. */
        LocalDate getCancellationDeadline() {
            return checkIn.minusDays(2);
        }

        void cancel(LocalDate today) {
            if (!active) {
                throw new IllegalStateException("Reservation is already cancelled.");
            }
            if (today.isAfter(getCancellationDeadline())) {
                throw new IllegalStateException("Cannot cancel: the cancellation deadline ("
                        + FMT.format(getCancellationDeadline()) + ") has passed.");
            }
            active = false;
        }

        String describe() {
            return customer.getName() + ", " + room.getLabel() + " (" + range(checkIn, checkOut) + ")";
        }
    }

    static class HotelService {
        boolean checkAvailability(Room room, LocalDate from, LocalDate to) {
            boolean free = room.isAvailable(from, to);
            System.out.println(room.getLabel() + (free ? " is available from " : " is not available from ")
                    + FMT.format(from) + " to " + FMT.format(to) + ".");
            return free;
        }

        Reservation reserve(Customer customer, Room room, LocalDate from, LocalDate to) {
            if (!to.isAfter(from)) {
                throw new IllegalArgumentException("Check-out must be after check-in.");
            }
            if (!room.isAvailable(from, to)) {
                System.out.println(room.getLabel() + " is not available from "
                        + FMT.format(from) + " to " + FMT.format(to) + ".");
                return null;
            }
            int nights = (int) ChronoUnit.DAYS.between(from, to);
            Reservation r = new Reservation(customer, room, from, to, room.calculatePrice(nights));
            room.addReservation(r);
            System.out.println("Reservation confirmed for " + r.describe() + ". Price: " + money(r.getPrice()) + ".");
            return r;
        }

        void cancel(Reservation reservation, LocalDate today) {
            try {
                reservation.cancel(today);
                System.out.println("Reservation for " + reservation.describe() + " cancelled successfully.");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        HotelService hotel = new HotelService();
        LocalDate today = LocalDate.of(2026, 12, 20);
        Customer a = new Customer("Customer A");
        Customer b = new Customer("Customer B");
        Customer c = new Customer("Customer C");
        Room standard101 = new StandardRoom(101);
        Room deluxe201 = new DeluxeRoom(201);

        hotel.checkAvailability(standard101, LocalDate.of(2027, 1, 1), LocalDate.of(2027, 1, 5));
        Reservation resA = hotel.reserve(a, standard101, LocalDate.of(2027, 1, 1), LocalDate.of(2027, 1, 5));
        hotel.reserve(b, standard101, LocalDate.of(2027, 1, 3), LocalDate.of(2027, 1, 7));
        hotel.cancel(resA, today);
        hotel.reserve(c, deluxe201, LocalDate.of(2027, 2, 10), LocalDate.of(2027, 2, 12));
    }
}
