public class A5_MembershipNumbersReferralCodesWeeklyCheckInSettlement {

    static class GymMember {
        private static int counter = 2000;
        private static int membersEnrolled = 0;

        public final String membershipNumber;
        private int monthlyFee;
        private int feesPaid;
        private String lastMode;

        public GymMember(int monthlyFee) {
            counter++;
            this.membershipNumber = "GYM-" + counter;
            this.monthlyFee = monthlyFee;
            membersEnrolled++;
        }

        void payFee(int amount) {
            feesPaid += amount;
        }

        void payFee(int amount, String mode) {
            this.lastMode = mode; // record the mode, then delegate — no duplicated logic
            payFee(amount);
        }

        int getFeesPaid() {
            return feesPaid;
        }

        static int getMembersEnrolled() {
            return membersEnrolled;
        }
    }

    static class GroupClassMember extends GymMember {
        private String className;

        public GroupClassMember(int monthlyFee, String className) {
            super(monthlyFee);
            this.className = className;
        }
    }

    static boolean isValidReferralCode(String code) {
        if (code == null || code.length() != 4) return false;
        if (code.charAt(0) != 'G') return false;
        if (!Character.isDigit(code.charAt(1))) return false;
        if (!Character.isDigit(code.charAt(2))) return false;
        if (!Character.isUpperCase(code.charAt(3))) return false;
        return true;
    }

    static String processWeeklyCheckIn(GymMember[] members) {
        int processed = 0, nullSkipped = 0, group = 0, individual = 0;
        for (GymMember member : members) {
            if (member == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (member instanceof GroupClassMember) group++;
            else individual++;
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        GymMember m1 = new GymMember(1000);
        System.out.println(m1.membershipNumber);
        System.out.println(GymMember.getMembersEnrolled());

        System.out.println(isValidReferralCode("G45B"));
        System.out.println(isValidReferralCode("G4B"));
        System.out.println(isValidReferralCode("X45B"));

        m1.payFee(500);
        m1.payFee(500, "UPI");
        System.out.println(m1.getFeesPaid());

        GymMember[] members = {
                new GroupClassMember(1500, "Zumba"),
                null,
                new GymMember(1000)
        };
        System.out.println(processWeeklyCheckIn(members));
    }
}
