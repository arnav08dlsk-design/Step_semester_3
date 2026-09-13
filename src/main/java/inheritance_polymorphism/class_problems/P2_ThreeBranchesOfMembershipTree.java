public class P2_ThreeBranchesOfMembershipTree {

    static class LibraryMember {
        private String memberId;
        private int borrowLimit;
        protected int booksBorrowed;

        public LibraryMember(String memberId, int borrowLimit) {
            if (memberId == null || memberId.trim().isEmpty() || memberId.length() < 4) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.memberId = memberId;
            this.borrowLimit = borrowLimit;
        }

        void borrowBook() {
            booksBorrowed++;
        }

        int getBooksBorrowed() {
            return booksBorrowed;
        }

        String displayInfo() {
            return "General Member | Books Borrowed: " + booksBorrowed;
        }
    }

    // Single inheritance
    static class StudentMember extends LibraryMember {
        protected String course;

        public StudentMember(String memberId, int borrowLimit, String course) {
            super(memberId, borrowLimit);
            this.course = course;
        }

        @Override
        String displayInfo() {
            return "Student Member | Course: " + course + " | Books Borrowed: " + booksBorrowed;
        }
    }

    // Multilevel inheritance: LibraryMember -> StudentMember -> HonorsStudentMember (3 deep)
    static class HonorsStudentMember extends StudentMember {
        private int bonusLimit;

        public HonorsStudentMember(String memberId, int borrowLimit, String course, int bonusLimit) {
            super(memberId, borrowLimit, course);
            this.bonusLimit = bonusLimit;
        }

        @Override
        String displayInfo() {
            return "Honors Student Member | Course: " + course + " | Bonus Limit: " + bonusLimit
                    + " | Books Borrowed: " + booksBorrowed;
        }
    }

    // Hierarchical inheritance: independent sibling branch off LibraryMember directly
    static class FacultyMember extends LibraryMember {
        private String department;

        public FacultyMember(String memberId, int borrowLimit, String department) {
            super(memberId, borrowLimit);
            this.department = department;
        }

        @Override
        String displayInfo() {
            return "Faculty Member | Department: " + department + " | Books Borrowed: " + booksBorrowed;
        }
    }

    static String classifyGeneration(LibraryMember member) {
        if (member instanceof HonorsStudentMember) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (member instanceof FacultyMember) {
            return "Hierarchical sibling (independent branch)";
        }
        if (member instanceof StudentMember) {
            return "Direct descendant (1 generation deep)";
        }
        return "Base Member (0 generations deep)";
    }

    static int getTotalBooksBorrowed(LibraryMember[] members) {
        int total = 0;
        for (LibraryMember member : members) {
            total += member.getBooksBorrowed();
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(new LibraryMember("STU1", 3).displayInfo());
        System.out.println(new StudentMember("STU2", 3, "CSE").displayInfo());
        System.out.println(new HonorsStudentMember("STU3", 3, "ECE", 2).displayInfo());
        System.out.println(new FacultyMember("STU4", 5, "Physics").displayInfo());

        HonorsStudentMember honorsMember = new HonorsStudentMember("HON1", 3, "ECE", 2);
        FacultyMember facultyMember = new FacultyMember("FAC1", 5, "Physics");
        System.out.println(classifyGeneration(honorsMember));
        System.out.println(classifyGeneration(facultyMember));

        StudentMember studentMember = new StudentMember("STU5", 3, "CSE");
        studentMember.borrowBook();
        studentMember.borrowBook();
        honorsMember.borrowBook();
        facultyMember.borrowBook();
        facultyMember.borrowBook();
        facultyMember.borrowBook();

        System.out.println(getTotalBooksBorrowed(new LibraryMember[]{studentMember, honorsMember, facultyMember}));
    }
}
