package oo_design.assignment_problems;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * The Assignment Submission Portal
 *
 * UML relationships:
 *   Assignment <|-- CodingAssignment, WrittenAssignment   generalization (own late-penalty rule)
 *   Student    1 --- 0..* Submission                       association
 *   Assignment 1 --- 0..* Submission                       association
 *   Submission state: SUBMITTED -> GRADED (no resubmission after grading)
 *   Late days are calculated by Assignment (it owns the due date); the Submission
 *   only asks it, so a new assignment type needs no change to the grading workflow.
 */
public class A2_AssignmentSubmissionPortal {

    enum Status {
        SUBMITTED("Submitted"), GRADED("Graded");

        final String label;

        Status(String label) {
            this.label = label;
        }
    }

    static String fmt(double v) {
        return v == Math.rint(v) ? String.valueOf((long) v) : String.format(java.util.Locale.US, "%.1f", v);
    }

    static class Student {
        private final String name;

        Student(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    abstract static class Assignment {
        private final String title;
        private final double maxMarks;
        private final LocalDate dueDate;

        Assignment(String title, double maxMarks, LocalDate dueDate) {
            this.title = title;
            this.maxMarks = maxMarks;
            this.dueDate = dueDate;
        }

        String getTitle() {
            return title;
        }

        double getMaxMarks() {
            return maxMarks;
        }

        long daysLate(LocalDate submittedOn) {
            return Math.max(0, ChronoUnit.DAYS.between(dueDate, submittedOn));
        }

        /** Each assignment type defines its own penalty: percent of marks lost per late day. */
        abstract int getPenaltyPercentPerDay();

        int penaltyPercent(long daysLate) {
            return (int) Math.min(100, getPenaltyPercentPerDay() * daysLate);
        }

        double applyLatePenalty(double awarded, long daysLate) {
            return awarded * (100 - penaltyPercent(daysLate)) / 100.0;
        }
    }

    static class CodingAssignment extends Assignment {
        CodingAssignment(String title, double maxMarks, LocalDate dueDate) {
            super(title, maxMarks, dueDate);
        }

        @Override
        int getPenaltyPercentPerDay() {
            return 10;
        }
    }

    static class WrittenAssignment extends Assignment {
        WrittenAssignment(String title, double maxMarks, LocalDate dueDate) {
            super(title, maxMarks, dueDate);
        }

        @Override
        int getPenaltyPercentPerDay() {
            return 20;
        }
    }

    static class Submission {
        private final Student student;
        private final Assignment assignment;
        private LocalDate submittedOn;
        private Status status = Status.SUBMITTED;
        private double finalMarks;

        Submission(Student student, Assignment assignment, LocalDate submittedOn) {
            this.student = student;
            this.assignment = assignment;
            this.submittedOn = submittedOn;
        }

        Student getStudent() {
            return student;
        }

        Assignment getAssignment() {
            return assignment;
        }

        Status getStatus() {
            return status;
        }

        double getFinalMarks() {
            return finalMarks;
        }

        long getDaysLate() {
            return assignment.daysLate(submittedOn);
        }

        void resubmit(LocalDate newDate) {
            if (status == Status.GRADED) {
                throw new IllegalStateException("Cannot resubmit: '" + assignment.getTitle()
                        + "' has already been graded.");
            }
            submittedOn = newDate;
        }

        void grade(double awarded) {
            if (status != Status.SUBMITTED) {
                throw new IllegalStateException("This submission has already been graded.");
            }
            if (awarded < 0 || awarded > assignment.getMaxMarks()) {
                throw new IllegalArgumentException("Marks must be between 0 and " + fmt(assignment.getMaxMarks()) + ".");
            }
            finalMarks = assignment.applyLatePenalty(awarded, getDaysLate());
            status = Status.GRADED;
        }
    }

    static class Portal {
        Submission submit(Student student, Assignment assignment, LocalDate date) {
            Submission s = new Submission(student, assignment, date);
            long late = s.getDaysLate();
            String when = late == 0 ? "on time" : late + (late == 1 ? " day late" : " days late");
            System.out.println(student.getName() + "'s submission for '" + assignment.getTitle()
                    + "' received (" + when + "). Status: " + s.getStatus().label + ".");
            return s;
        }

        void grade(Submission s, double awarded) {
            long late = s.getDaysLate();
            s.grade(awarded);
            String penalty = late == 0 ? "" : " after " + s.getAssignment().penaltyPercent(late) + "% late penalty";
            System.out.println(s.getStudent().getName() + " graded: " + fmt(s.getFinalMarks()) + "/"
                    + fmt(s.getAssignment().getMaxMarks()) + penalty + ". Status: " + s.getStatus().label + ".");
        }

        void resubmit(Submission s, LocalDate date) {
            try {
                s.resubmit(date);
                System.out.println(s.getStudent().getName() + " resubmitted '" + s.getAssignment().getTitle() + "'.");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Portal portal = new Portal();
        Assignment lab = new CodingAssignment("Linked List Lab", 50, LocalDate.of(2027, 3, 10));
        Assignment essay = new WrittenAssignment("Design Essay", 50, LocalDate.of(2027, 3, 12));
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");

        Submission ashaSub = portal.submit(asha, lab, LocalDate.of(2027, 3, 10));
        Submission raviSub = portal.submit(ravi, essay, LocalDate.of(2027, 3, 14));
        portal.grade(ashaSub, 45);
        portal.grade(raviSub, 40);
        portal.resubmit(ashaSub, LocalDate.of(2027, 3, 15));
    }
}
