package oo_design.assignment_problems;

import java.util.Locale;

/**
 * The Hostel Laundry Queue
 *
 * UML relationships:
 *   WashType <|-- QuickWash, NormalWash, HeavyWash   generalization (each type owns duration + charge)
 *   Student 1 --- 0..* WashCycle                     association
 *   WashingMachine 1 --- 0..* WashCycle              association (only one RUNNING cycle at a time)
 *   WashCycle 0..* --- 1 WashType                    association
 *   LaundryDesk ..> WashType                         depends on the abstraction, so a new type
 *                                                    (e.g. Delicate) needs no change to the booking logic
 *   Machine status is private: it only changes through start() and completeCycle().
 */
public class A1_HostelLaundryQueue {

    static String money(double v) {
        return String.format(Locale.US, "₹%.2f", v);
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

    abstract static class WashType {
        abstract String getName();

        abstract int getDurationMinutes();

        abstract double getCharge();
    }

    static class QuickWash extends WashType {
        @Override
        String getName() {
            return "Quick";
        }

        @Override
        int getDurationMinutes() {
            return 30;
        }

        @Override
        double getCharge() {
            return 20.0;
        }
    }

    static class NormalWash extends WashType {
        @Override
        String getName() {
            return "Normal";
        }

        @Override
        int getDurationMinutes() {
            return 45;
        }

        @Override
        double getCharge() {
            return 30.0;
        }
    }

    static class HeavyWash extends WashType {
        @Override
        String getName() {
            return "Heavy";
        }

        @Override
        int getDurationMinutes() {
            return 60;
        }

        @Override
        double getCharge() {
            return 45.0;
        }
    }

    static class WashCycle {
        private final Student student;
        private final WashingMachine machine;
        private final WashType type;

        WashCycle(Student student, WashingMachine machine, WashType type) {
            this.student = student;
            this.machine = machine;
            this.type = type;
        }

        Student getStudent() {
            return student;
        }

        WashingMachine getMachine() {
            return machine;
        }

        WashType getType() {
            return type;
        }
    }

    static class WashingMachine {
        private final String id;
        private boolean busy = false;
        private WashCycle currentCycle;

        WashingMachine(String id) {
            this.id = id;
        }

        String getId() {
            return id;
        }

        boolean isBusy() {
            return busy;
        }

        WashCycle start(Student student, WashType type) {
            if (busy) {
                throw new IllegalStateException("Machine " + id + " is currently busy.");
            }
            busy = true;
            currentCycle = new WashCycle(student, this, type);
            return currentCycle;
        }

        void completeCycle() {
            if (!busy) {
                throw new IllegalStateException("Machine " + id + " has no wash in progress.");
            }
            busy = false;
            currentCycle = null;
        }
    }

    static class LaundryDesk {
        WashCycle startWash(Student student, WashingMachine machine, WashType type) {
            try {
                WashCycle cycle = machine.start(student, type);
                System.out.println(type.getName() + " wash started on " + machine.getId() + " for "
                        + student.getName() + " (" + type.getDurationMinutes() + " min). Charge: "
                        + money(type.getCharge()) + ".");
                return cycle;
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }

        void completeWash(WashingMachine machine) {
            machine.completeCycle();
            System.out.println(machine.getId() + " cycle completed. " + machine.getId() + " is now free.");
        }
    }

    public static void main(String[] args) {
        LaundryDesk desk = new LaundryDesk();
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        desk.startWash(asha, m1, new QuickWash());
        desk.startWash(ravi, m1, new HeavyWash());   // M1 busy
        desk.startWash(ravi, m2, new HeavyWash());
        desk.completeWash(m1);
        desk.startWash(neha, m1, new NormalWash());
    }
}
