package oo_design.assignment_problems;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * The Campus Premiere Ticket Counter
 *
 * UML relationships:
 *   Seat <|-- RegularSeat, PremiumSeat, ReclinerSeat   generalization (each category owns its price)
 *   Customer 1 --- 0..* Booking          association
 *   Show     1 --- 0..* Booking          association
 *   Booking  1 --- 1..6 Seat             a booking holds 1 to 6 seats
 *   Show keeps the set of already-booked seat labels, so a seat is never sold twice for one show.
 *   Cancelling a booking releases its seats back to the Show.
 */
public class A3_CampusPremiereTicketCounter {

    static final int MAX_SEATS_PER_BOOKING = 6;

    static String money(double v) {
        return String.format(Locale.US, "₹%,.2f", v);
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

    abstract static class Seat {
        private final String label;

        Seat(String label) {
            this.label = label;
        }

        String getLabel() {
            return label;
        }

        abstract int getPrice();
    }

    static class RegularSeat extends Seat {
        RegularSeat(String label) {
            super(label);
        }

        @Override
        int getPrice() {
            return 150;
        }
    }

    static class PremiumSeat extends Seat {
        PremiumSeat(String label) {
            super(label);
        }

        @Override
        int getPrice() {
            return 250;
        }
    }

    static class ReclinerSeat extends Seat {
        ReclinerSeat(String label) {
            super(label);
        }

        @Override
        int getPrice() {
            return 400;
        }
    }

    static class Show {
        private final String name;
        private final LocalDateTime startTime;
        private final Set<String> bookedSeats = new HashSet<String>();

        Show(String name, LocalDateTime startTime) {
            this.name = name;
            this.startTime = startTime;
        }

        String getName() {
            return name;
        }

        LocalDateTime getStartTime() {
            return startTime;
        }

        boolean isBooked(String label) {
            return bookedSeats.contains(label);
        }

        void reserve(List<Seat> seats) {
            for (Seat s : seats) {
                bookedSeats.add(s.getLabel());
            }
        }

        void release(List<Seat> seats) {
            for (Seat s : seats) {
                bookedSeats.remove(s.getLabel());
            }
        }
    }

    static class Booking {
        private final Customer customer;
        private final Show show;
        private final List<Seat> seats;
        private boolean cancelled = false;

        Booking(Customer customer, Show show, List<Seat> seats) {
            this.customer = customer;
            this.show = show;
            this.seats = Collections.unmodifiableList(new ArrayList<Seat>(seats));
        }

        Customer getCustomer() {
            return customer;
        }

        Show getShow() {
            return show;
        }

        List<Seat> getSeats() {
            return seats;
        }

        boolean isCancelled() {
            return cancelled;
        }

        int getTotal() {
            int total = 0;
            for (Seat s : seats) {
                total += s.getPrice();
            }
            return total;
        }

        String seatLabels() {
            List<String> labels = new ArrayList<String>();
            for (Seat s : seats) {
                labels.add(s.getLabel());
            }
            return String.join(", ", labels);
        }

        void cancel(LocalDateTime now) {
            if (cancelled) {
                throw new IllegalStateException("Booking is already cancelled.");
            }
            if (!now.isBefore(show.getStartTime())) {
                throw new IllegalStateException("Cannot cancel: the show has already started.");
            }
            cancelled = true;
            show.release(seats);
        }
    }

    static class BookingService {
        Booking book(Customer customer, Show show, List<Seat> seats) {
            if (seats.isEmpty() || seats.size() > MAX_SEATS_PER_BOOKING) {
                System.out.println("A booking needs between 1 and " + MAX_SEATS_PER_BOOKING + " seats.");
                return null;
            }
            Set<String> requested = new HashSet<String>();
            for (Seat s : seats) {
                if (!requested.add(s.getLabel()) || show.isBooked(s.getLabel())) {
                    System.out.println("Seat " + s.getLabel() + " is already booked for this show.");
                    return null;
                }
            }
            show.reserve(seats);
            Booking booking = new Booking(customer, show, seats);
            System.out.println("Booking confirmed for " + customer.getName() + ": " + booking.seatLabels()
                    + ". Total: " + money(booking.getTotal()) + ".");
            return booking;
        }

        void cancel(Booking booking, LocalDateTime now) {
            try {
                booking.cancel(now);
                System.out.println(booking.getCustomer().getName() + "'s booking cancelled. Seats "
                        + booking.seatLabels() + " released.");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        BookingService service = new BookingService();
        Show show = new Show("Campus Premiere", LocalDateTime.of(2027, 3, 20, 19, 0));
        LocalDateTime now = LocalDateTime.of(2027, 3, 20, 15, 0);
        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Booking ashaBooking = service.book(asha, show,
                Arrays.<Seat>asList(new RegularSeat("A1"), new RegularSeat("A2"), new PremiumSeat("F5")));
        service.book(ravi, show, Arrays.<Seat>asList(new RegularSeat("A2")));          // already booked
        service.book(ravi, show, Arrays.<Seat>asList(new ReclinerSeat("R1")));
        service.cancel(ashaBooking, now);
        service.book(neha, show, Arrays.<Seat>asList(new RegularSeat("A2")));
    }
}
