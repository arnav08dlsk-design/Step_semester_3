public class P4_WeeklyCirculationReport {

    static class LibraryMember {
        private String memberId;
        private int borrowLimit;
        protected int booksBorrowed;

        public LibraryMember(String memberId, int borrowLimit) {
            this.memberId = memberId;
            this.borrowLimit = borrowLimit;
        }

        String displayInfo() {
            return "General | Books: " + booksBorrowed + " ";
        }
    }

    static class StudentMember extends LibraryMember {
        String course;

        public StudentMember(String memberId, int borrowLimit, String course) {
            super(memberId, borrowLimit);
            this.course = course;
        }

        @Override
        String displayInfo() {
            return "Student | Course: " + course + " | Books: " + booksBorrowed + " ";
        }
    }

    static String batchPrint(LibraryMember[] members) {
        StringBuilder sb = new StringBuilder();
        for (LibraryMember member : members) {
            sb.append(member.displayInfo()); // polymorphic call, no instanceof chain here
            if (member instanceof StudentMember) {
                StudentMember sm = (StudentMember) member; // check first, then cast
                sb.append("[Course via downcast: ").append(sm.course).append("] ");
            }
            sb.append("| ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String report = batchPrint(new LibraryMember[]{
                new LibraryMember("LB5", 3),
                new StudentMember("STU6", 3, "ECE")
        });
        System.out.println(report);

        try {
            LibraryMember plain = new LibraryMember("LB6", 3);
            StudentMember bad = (StudentMember) plain;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}
