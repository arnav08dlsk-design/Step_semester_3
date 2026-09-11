public class P5_ImmutableBookingReceiptAndNightlySettlement {

    // Note: the brief asks for BookingReceipt to be a "final class", but also asks for
    // GroupBookingReceipt to extend it — a literal `final` class cannot be extended in Java,
    // so this class is kept effectively immutable (final fields, defensive copies, no mutators
    // except the with-style method) without the `final` keyword, so the required subclass compiles.
    static class BookingReceipt {
        private final String bookingId;
        private final String[] seatNumbers;

        public BookingReceipt(String bookingId, String[] seatNumbers) {
            this.bookingId = bookingId;
            this.seatNumbers = seatNumbers.clone(); // defensive copy IN
        }

        public String getBookingId() {
            return bookingId;
        }

        public String[] getSeatNumbers() {
            return seatNumbers.clone(); // defensive copy OUT
        }

        public BookingReceipt withUpdatedSeat(int index, String newSeat) {
            String[] newSeats = seatNumbers.clone();
            newSeats[index] = newSeat;
            return new BookingReceipt(bookingId, newSeats);
        }
    }

    static class GroupBookingReceipt extends BookingReceipt {
        private final int groupSize;

        public GroupBookingReceipt(String bookingId, String[] seatNumbers, int groupSize) {
            super(bookingId, seatNumbers);
            this.groupSize = groupSize;
        }

        public int getGroupSize() {
            return groupSize;
        }
    }

    static String processNightlySettlement(BookingReceipt[] receipts) {
        int processed = 0, nullSkipped = 0, group = 0, individual = 0;
        for (BookingReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (receipt instanceof GroupBookingReceipt) {
                group++;
            } else {
                individual++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        BookingReceipt b = new BookingReceipt("CH-1001", new String[]{"A1", "A2"});
        String[] seats = b.getSeatNumbers();
        seats[0] = "X"; // mutating the returned array must never touch internal state
        System.out.println(b.getSeatNumbers()[0]);

        BookingReceipt updated = b.withUpdatedSeat(1, "A3");
        System.out.println(java.util.Arrays.toString(b.getSeatNumbers()));
        System.out.println(java.util.Arrays.toString(updated.getSeatNumbers()));

        BookingReceipt[] receipts = {
                new GroupBookingReceipt("CH-2002", new String[]{"B1", "B2"}, 2),
                null,
                new BookingReceipt("CH-3003", new String[]{"C1"})
        };
        System.out.println(processNightlySettlement(receipts));
    }
}
