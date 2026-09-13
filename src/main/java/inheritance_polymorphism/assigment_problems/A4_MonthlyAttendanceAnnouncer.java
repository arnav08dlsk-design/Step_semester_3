public class A4_MonthlyAttendanceAnnouncer {

    static class GymMember {
        private String memberId;
        private int monthlyFee;
        protected int sessionsAttended;

        public GymMember(String memberId, int monthlyFee) {
            this.memberId = memberId;
            this.monthlyFee = monthlyFee;
        }

        String displayInfo() {
            return "Standard | Sessions: " + sessionsAttended + " ";
        }
    }

    static class PremiumMember extends GymMember {
        String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }

        @Override
        String displayInfo() {
            return "Premium | Trainer: " + trainerName + " | Sessions: " + sessionsAttended + " ";
        }
    }

    static String batchPrint(GymMember[] members) {
        StringBuilder sb = new StringBuilder();
        for (GymMember member : members) {
            sb.append(member.displayInfo()); // polymorphic call, no instanceof chain here
            if (member instanceof PremiumMember) {
                PremiumMember pm = (PremiumMember) member; // check first, then cast
                sb.append("[Trainer via downcast: ").append(pm.trainerName).append("] ");
            }
            sb.append("| ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String announcement = batchPrint(new GymMember[]{
                new GymMember("MEM6", 1000),
                new PremiumMember("MEM7", 2000, "Coach Riya")
        });
        System.out.println(announcement);

        try {
            GymMember plain = new GymMember("MEM8", 1000);
            PremiumMember bad = (PremiumMember) plain;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}
