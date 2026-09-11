public class P3_SeatBookingEncapsulationGuard {

    static class CineScreen {
        private int seatsTotal;
        private int seatsAvailable;

        CineScreen(int seatsTotal) {
            if (seatsTotal <= 0) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.seatsTotal = seatsTotal;
            this.seatsAvailable = seatsTotal;
        }

        void bookSeat() {
            if (seatsAvailable > 0) {
                seatsAvailable--;
            }
            // else: silently rejected, no seat left to take
        }

        void cancelBooking() {
            if (seatsAvailable < seatsTotal) {
                seatsAvailable++;
            }
            // else: silently rejected, screen is already back to full
        }

        int getSeatsAvailable() {
            return seatsAvailable;
        }
    }

    public static void main(String[] args) {
        try {
            new CineScreen(0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        CineScreen c = new CineScreen(2);
        c.bookSeat();
        c.bookSeat();
        c.bookSeat(); // 3rd booking, rejected
        System.out.println(c.getSeatsAvailable());

        c.cancelBooking();
        c.cancelBooking();
        c.cancelBooking(); // 3rd cancellation, rejected
        System.out.println(c.getSeatsAvailable());
    }
}
