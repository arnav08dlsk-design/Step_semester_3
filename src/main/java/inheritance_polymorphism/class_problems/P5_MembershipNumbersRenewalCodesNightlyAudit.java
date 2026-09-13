public class P5_MembershipNumbersRenewalCodesNightlyAudit {

    static class LibraryMember {
        private static int counter = 100;
        private static int membersEnrolled = 0;

        public final String memberNumber;
        private int borrowLimit;
        protected int booksBorrowed;
        private String lastGenre;

        public LibraryMember(int borrowLimit) {
            counter++;
            this.memberNumber = "LIB-" + counter;
            this.borrowLimit = borrowLimit;
            membersEnrolled++;
        }

        void borrowBook() {
            booksBorrowed++;
        }

        void borrowBook(String genre) {
            this.lastGenre = genre; // record the genre, then delegate — no duplicated logic
            borrowBook();
        }

        int getBooksBorrowed() {
            return booksBorrowed;
        }

        static int getMembersEnrolled() {
            return membersEnrolled;
        }
    }

    static class FacultyMember extends LibraryMember {
        private String department;

        public FacultyMember(int borrowLimit, String department) {
            super(borrowLimit);
            this.department = department;
        }
    }

    static boolean isValidRenewalCode(String code) {
        if (code == null || code.length() != 4) return false;
        if (code.charAt(0) != 'R') return false;
        if (!Character.isDigit(code.charAt(1))) return false;
        if (!Character.isDigit(code.charAt(2))) return false;
        if (!Character.isUpperCase(code.charAt(3))) return false;
        return true;
    }

    static String processNightlyAudit(LibraryMember[] members) {
        int processed = 0, nullSkipped = 0, faculty = 0, regular = 0;
        for (LibraryMember member : members) {
            if (member == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (member instanceof FacultyMember) faculty++;
            else regular++;
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + faculty + " faculty | " + regular + " regular";
    }

    public static void main(String[] args) {
        LibraryMember m1 = new LibraryMember(3);
        System.out.println(m1.memberNumber);
        System.out.println(LibraryMember.getMembersEnrolled());

        System.out.println(isValidRenewalCode("R12A"));
        System.out.println(isValidRenewalCode("R1A"));
        System.out.println(isValidRenewalCode("X12A"));

        m1.borrowBook();
        m1.borrowBook("Fiction");
        System.out.println(m1.getBooksBorrowed());

        LibraryMember[] members = {
                new FacultyMember(5, "Physics"),
                null,
                new LibraryMember(3)
        };
        System.out.println(processNightlyAudit(members));
    }
}
