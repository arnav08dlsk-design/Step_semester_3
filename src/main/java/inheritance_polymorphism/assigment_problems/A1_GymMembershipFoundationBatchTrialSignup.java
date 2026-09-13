public class A1_GymMembershipFoundationBatchTrialSignup {

    static class GymMember {
        private String memberId;
        private int monthlyFee;
        protected int sessionsAttended;

        public GymMember(String memberId, int monthlyFee) {
            if (memberId == null || memberId.trim().isEmpty() || memberId.length() < 4) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.memberId = memberId;
            this.monthlyFee = monthlyFee;
        }

        void attendSession() {
            sessionsAttended++;
        }

        int getSessionsAttended() {
            return sessionsAttended;
        }
    }

    static class PremiumMember extends GymMember {
        private String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }
    }

    static String signUpBatch(String[] memberIds, int monthlyFee) {
        int signedUp = 0, rejected = 0;
        for (String id : memberIds) {
            try {
                new GymMember(id, monthlyFee);
                signedUp++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Signed Up: " + signedUp + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        try {
            new GymMember("GM1", 1000);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        PremiumMember p = new PremiumMember("MEM01", 2000, "Coach Riya");
        p.attendSession();
        p.attendSession();
        System.out.println(p.getSessionsAttended());

        System.out.println(signUpBatch(new String[]{"MEM1", "GM1", "MEM2", " ", "MEM3"}, 1000));
    }
}
