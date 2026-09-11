public class A5_ImmutableLoanReceiptAndNightlyCirculationLedger {

    // Note: the brief asks for LoanReceipt to be a "final class" while also requiring
    // ReferenceOnlyLoanReceipt to extend it — a literal `final` class cannot be extended in
    // Java, so this class stays effectively immutable (final fields, defensive copies, only a
    // with-style mutator) without the `final` keyword, so the required subclass compiles.
    static class LoanReceipt {
        private final String memberId;
        private final String[] bookIds;

        public LoanReceipt(String memberId, String[] bookIds) {
            this.memberId = memberId;
            this.bookIds = bookIds.clone(); // defensive copy IN
        }

        public String getMemberId() {
            return memberId;
        }

        public String[] getBookIds() {
            return bookIds.clone(); // defensive copy OUT
        }

        public LoanReceipt withCorrectedBookId(int index, String newId) {
            String[] newBookIds = bookIds.clone();
            newBookIds[index] = newId;
            return new LoanReceipt(memberId, newBookIds);
        }
    }

    static class ReferenceOnlyLoanReceipt extends LoanReceipt {
        private final String roomNumber;

        public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
            super(memberId, bookIds);
            this.roomNumber = roomNumber;
        }

        public String getRoomNumber() {
            return roomNumber;
        }
    }

    static class CirculationLedger {
        static String branchCode;

        static {
            branchCode = "PT-CENTRAL";
        }
    }

    static String processNightlyCirculation(LoanReceipt[] receipts) {
        int processed = 0, nullSkipped = 0, referenceOnly = 0, regular = 0;
        for (LoanReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + referenceOnly
                + " reference-only | " + regular + " regular";
    }

    public static void main(String[] args) {
        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println(r.getBookIds()[0]);

        LoanReceipt corrected = r.withCorrectedBookId(1, "BK-102");
        System.out.println(java.util.Arrays.toString(r.getBookIds()));
        System.out.println(java.util.Arrays.toString(corrected.getBookIds()));

        LoanReceipt[] receipts = {
                new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
                null,
                new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };
        System.out.println(processNightlyCirculation(receipts));
    }
}
