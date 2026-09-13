import java.util.Arrays;

public class P3_StudentDiscountFineLedger {

    static class LibraryMember {
        private String memberId;
        private int borrowLimit;
        private int[] fineHistory = new int[10];
        private int fineCount = 0;

        public LibraryMember(String memberId, int borrowLimit) {
            if (memberId == null || memberId.trim().isEmpty() || memberId.length() < 4) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.memberId = memberId;
            this.borrowLimit = borrowLimit;
        }

        protected void chargeFine(int amount) {
            fineHistory[fineCount] = amount;
            fineCount++;
        }

        int[] getFineHistory() {
            return Arrays.copyOf(fineHistory, fineCount); // defensive copy, every time
        }

        int getTotalFine() {
            int total = 0;
            for (int i = 0; i < fineCount; i++) total += fineHistory[i];
            return total;
        }
    }

    static class StudentMember extends LibraryMember {
        private String course;

        public StudentMember(String memberId, int borrowLimit, String course) {
            super(memberId, borrowLimit);
            this.course = course;
        }

        @Override
        protected void chargeFine(int amount) {
            super.chargeFine(amount / 2); // reuse parent's deduction + recording logic
        }
    }

    public static void main(String[] args) {
        StudentMember s = new StudentMember("STU5", 3, "CSE");
        s.chargeFine(100);
        System.out.println(s.getTotalFine());

        int[] history = s.getFineHistory();
        history[0] = 999; // tampering with the returned copy
        System.out.println(Arrays.toString(s.getFineHistory()));
    }
}
