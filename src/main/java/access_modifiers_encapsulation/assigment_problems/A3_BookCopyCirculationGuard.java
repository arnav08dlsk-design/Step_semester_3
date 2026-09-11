public class A3_BookCopyCirculationGuard {

    static class BookInventory {
        private int copiesTotal;
        private int copiesAvailable;

        BookInventory(int copiesTotal) {
            this.copiesTotal = copiesTotal;
            this.copiesAvailable = copiesTotal;
        }

        void checkOut() {
            if (copiesAvailable > 0) {
                copiesAvailable--;
            }
            // else: silently rejected, nothing left to check out
        }

        void checkIn() {
            if (copiesAvailable < copiesTotal) {
                copiesAvailable++;
            }
            // else: silently rejected, already at full capacity
        }

        int getCopiesAvailable() {
            return copiesAvailable;
        }
    }

    public static void main(String[] args) {
        BookInventory b = new BookInventory(3);
        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut(); // 4th attempt, rejected
        System.out.println(b.getCopiesAvailable());

        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn(); // 4th attempt, rejected
        System.out.println(b.getCopiesAvailable());
    }
}
