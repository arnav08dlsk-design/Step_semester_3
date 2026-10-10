package oo_design.class_problems;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Employee Leave Request Workflow
 *
 * UML relationships:
 *   Employee <|-- FullTimeEmployee, PartTimeEmployee, Contractor   generalization (own leave rules)
 *   Employee 1 --- 0..* LeaveRequest                               association
 *   Manager  1 --- 0..* LeaveRequest                               reviews
 *   LeaveRequest state: PENDING -> APPROVED | REJECTED  (terminal, never back to PENDING)
 *   LeaveService depends only on Employee, so a new employee type needs no workflow change.
 */
public class P2_EmployeeLeaveWorkflow {

    enum Status {
        PENDING("Pending"), APPROVED("Approved"), REJECTED("Rejected");

        final String label;

        Status(String label) {
            this.label = label;
        }
    }

    static String range(LocalDate s, LocalDate e) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH);
        if (s.getMonth() == e.getMonth() && s.getYear() == e.getYear()) {
            return f.format(s) + "-" + e.getDayOfMonth();
        }
        return f.format(s) + " - " + f.format(e);
    }

    abstract static class Employee {
        private final String name;

        Employee(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        /** Each employee type defines its own leave policy. */
        abstract int getMaxLeaveDays();
    }

    static class FullTimeEmployee extends Employee {
        FullTimeEmployee(String name) {
            super(name);
        }

        @Override
        int getMaxLeaveDays() {
            return 20;
        }
    }

    static class PartTimeEmployee extends Employee {
        PartTimeEmployee(String name) {
            super(name);
        }

        @Override
        int getMaxLeaveDays() {
            return 10;
        }
    }

    static class Contractor extends Employee {
        Contractor(String name) {
            super(name);
        }

        @Override
        int getMaxLeaveDays() {
            return 5;
        }
    }

    static class LeaveRequest {
        private final Employee employee;
        private final LocalDate start;
        private final LocalDate end;
        private Status status = Status.PENDING;

        LeaveRequest(Employee employee, LocalDate start, LocalDate end) {
            this.employee = employee;
            this.start = start;
            this.end = end;
        }

        Employee getEmployee() {
            return employee;
        }

        Status getStatus() {
            return status;
        }

        long getDays() {
            return ChronoUnit.DAYS.between(start, end) + 1;
        }

        String getRange() {
            return range(start, end);
        }

        /** Only a PENDING request can move, and only to APPROVED or REJECTED. */
        void changeStatus(Status next) {
            if (status != Status.PENDING || next == Status.PENDING) {
                throw new IllegalStateException("Cannot change leave request status from "
                        + status.label + " to " + next.label + ".");
            }
            status = next;
        }
    }

    static class Manager {
        private final String name;

        Manager(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        void review(LeaveRequest request, boolean approve) {
            String who = request.getEmployee().getName();
            if (approve) {
                request.changeStatus(Status.APPROVED);
                System.out.println(who + "'s leave request (" + request.getRange() + ") approved. Status: "
                        + request.getStatus().label + ".");
            } else {
                request.changeStatus(Status.REJECTED);
                System.out.println(who + "'s leave request (" + request.getRange() + ") rejected. Status: "
                        + request.getStatus().label + ".");
            }
        }
    }

    static class LeaveService {
        LeaveRequest submit(Employee employee, LocalDate start, LocalDate end) {
            if (end.isBefore(start)) {
                throw new IllegalArgumentException("End date cannot be before start date.");
            }
            LeaveRequest request = new LeaveRequest(employee, start, end);
            if (request.getDays() > employee.getMaxLeaveDays()) {
                System.out.println("Leave request denied for " + employee.getName()
                        + ": exceeds the limit of " + employee.getMaxLeaveDays() + " days.");
                return null;
            }
            System.out.println("Leave request submitted for " + employee.getName() + " ("
                    + request.getRange() + "). Status: " + request.getStatus().label + ".");
            return request;
        }
    }

    public static void main(String[] args) {
        LeaveService service = new LeaveService();
        Employee john = new FullTimeEmployee("John");
        Employee jane = new PartTimeEmployee("Jane");
        Manager alice = new Manager("Alice");
        Manager bob = new Manager("Bob");

        LeaveRequest johnReq = service.submit(john, LocalDate.of(2027, 1, 1), LocalDate.of(2027, 1, 5));
        alice.review(johnReq, true);

        LeaveRequest janeReq = service.submit(jane, LocalDate.of(2027, 2, 10), LocalDate.of(2027, 2, 11));
        bob.review(janeReq, false);

        try {
            johnReq.changeStatus(Status.PENDING);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
