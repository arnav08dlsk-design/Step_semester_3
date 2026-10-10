package oo_design.assignment_problems;

import java.util.Locale;

/**
 * The FitZone Membership Desk
 *
 * UML relationships:
 *   MembershipPlan <|-- MonthlyPlan, QuarterlyPlan, AnnualPlan   generalization (own fee rule)
 *   Member 1 --- 0..* Membership         association
 *   Membership 0..* --- 1 MembershipPlan association
 *   Membership state: ACTIVE <-> FROZEN, ACTIVE/FROZEN -> EXPIRED (terminal)
 *   Fee rules live in the plan classes and status rules live in Membership, kept apart,
 *   so a new plan (e.g. Half-Yearly) needs no change to the membership workflow.
 */
public class A4_FitZoneMembershipDesk {

    static final double BASE_RATE_PER_MONTH = 1000.0;

    enum Status {
        ACTIVE("Active"), FROZEN("Frozen"), EXPIRED("Expired");

        final String label;

        Status(String label) {
            this.label = label;
        }
    }

    static String money(double v) {
        return String.format(Locale.US, "₹%,.2f", v);
    }

    static String article(String word) {
        return "AEIOUaeiou".indexOf(word.charAt(0)) >= 0 ? "an " : "a ";
    }

    static class Member {
        private final String name;

        Member(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    abstract static class MembershipPlan {
        abstract String getName();

        /** Each plan applies its own discount rule to the base rate. */
        abstract double calculateFee();
    }

    static class MonthlyPlan extends MembershipPlan {
        @Override
        String getName() {
            return "Monthly";
        }

        @Override
        double calculateFee() {
            return BASE_RATE_PER_MONTH * 1;
        }
    }

    static class QuarterlyPlan extends MembershipPlan {
        @Override
        String getName() {
            return "Quarterly";
        }

        @Override
        double calculateFee() {
            return BASE_RATE_PER_MONTH * 3 * 0.90;
        }
    }

    static class AnnualPlan extends MembershipPlan {
        @Override
        String getName() {
            return "Annual";
        }

        @Override
        double calculateFee() {
            return BASE_RATE_PER_MONTH * 12 * 0.75;
        }
    }

    static class Membership {
        private final Member member;
        private final MembershipPlan plan;
        private final double fee;
        private Status status = Status.ACTIVE;

        Membership(Member member, MembershipPlan plan) {
            this.member = member;
            this.plan = plan;
            this.fee = plan.calculateFee();
        }

        Member getMember() {
            return member;
        }

        MembershipPlan getPlan() {
            return plan;
        }

        double getFee() {
            return fee;
        }

        Status getStatus() {
            return status;
        }

        void checkIn() {
            if (status != Status.ACTIVE) {
                throw new IllegalStateException("Check-in denied: " + member.getName()
                        + "'s membership is " + status.label + ".");
            }
        }

        void freeze() {
            if (status != Status.ACTIVE) {
                throw new IllegalStateException("Cannot freeze " + article(status.label) + status.label + " membership.");
            }
            status = Status.FROZEN;
        }

        void unfreeze() {
            if (status != Status.FROZEN) {
                throw new IllegalStateException("Cannot unfreeze " + article(status.label) + status.label + " membership.");
            }
            status = Status.ACTIVE;
        }

        void expire() {
            if (status == Status.EXPIRED) {
                throw new IllegalStateException("Membership is already Expired.");
            }
            status = Status.EXPIRED;
        }
    }

    static class MembershipDesk {
        Membership buy(Member member, MembershipPlan plan) {
            Membership m = new Membership(member, plan);
            System.out.println(plan.getName() + " membership created for " + member.getName()
                    + ". Fee: " + money(m.getFee()) + ". Status: " + m.getStatus().label + ".");
            return m;
        }

        void checkIn(Membership m) {
            try {
                m.checkIn();
                System.out.println(m.getMember().getName() + " checked in successfully.");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }

        void freeze(Membership m) {
            try {
                m.freeze();
                System.out.println(m.getMember().getName() + "'s membership frozen. Status: " + m.getStatus().label + ".");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }

        void unfreeze(Membership m) {
            try {
                m.unfreeze();
                System.out.println(m.getMember().getName() + "'s membership unfrozen. Status: " + m.getStatus().label + ".");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }

        void expire(Membership m) {
            try {
                m.expire();
                System.out.println(m.getMember().getName() + "'s membership expired. Status: " + m.getStatus().label + ".");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        MembershipDesk desk = new MembershipDesk();
        Member asha = new Member("Asha");
        Member ravi = new Member("Ravi");

        Membership ashaM = desk.buy(asha, new QuarterlyPlan());
        Membership raviM = desk.buy(ravi, new MonthlyPlan());
        desk.checkIn(ashaM);
        desk.freeze(ashaM);
        desk.checkIn(ashaM);   // denied: frozen
        desk.expire(raviM);
        desk.freeze(raviM);    // denied: expired
    }
}
