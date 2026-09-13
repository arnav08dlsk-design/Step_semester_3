public class A2_ThreeTiersOfGymMembership {

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

        String displayInfo() {
            return "Standard Member | Sessions: " + sessionsAttended;
        }
    }

    // Single inheritance
    static class PremiumMember extends GymMember {
        protected String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }

        @Override
        String displayInfo() {
            return "Premium Member | Trainer: " + trainerName + " | Sessions: " + sessionsAttended;
        }
    }

    // Multilevel inheritance: GymMember -> PremiumMember -> EliteMember (3 deep)
    static class EliteMember extends PremiumMember {
        private String lockerNumber;

        public EliteMember(String memberId, int monthlyFee, String trainerName, String lockerNumber) {
            super(memberId, monthlyFee, trainerName);
            this.lockerNumber = lockerNumber;
        }

        @Override
        String displayInfo() {
            return "Elite Member | Trainer: " + trainerName + " | Locker: " + lockerNumber + " | Sessions: " + sessionsAttended;
        }
    }

    // Hierarchical inheritance: independent sibling branch off GymMember directly
    static class GroupClassMember extends GymMember {
        private String className;

        public GroupClassMember(String memberId, int monthlyFee, String className) {
            super(memberId, monthlyFee);
            this.className = className;
        }

        @Override
        String displayInfo() {
            return "Group Class Member | Class: " + className + " | Sessions: " + sessionsAttended;
        }
    }

    static String classifyGeneration(GymMember member) {
        if (member instanceof EliteMember) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (member instanceof GroupClassMember) {
            return "Hierarchical sibling (independent branch)";
        }
        if (member instanceof PremiumMember) {
            return "Direct descendant (1 generation deep)";
        }
        return "Base Member (0 generations deep)";
    }

    static int getTotalSessionsAttended(GymMember[] members) {
        int total = 0;
        for (GymMember member : members) {
            total += member.getSessionsAttended();
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(new GymMember("MEM1", 1000).displayInfo());
        System.out.println(new PremiumMember("MEM2", 2000, "Coach Riya").displayInfo());
        System.out.println(new EliteMember("MEM3", 3000, "Coach Arjun", "L12").displayInfo());
        System.out.println(new GroupClassMember("MEM4", 1500, "Zumba").displayInfo());

        EliteMember eliteMember = new EliteMember("ELT1", 3000, "Coach Arjun", "L12");
        GroupClassMember groupClassMember = new GroupClassMember("GRP1", 1500, "Zumba");
        System.out.println(classifyGeneration(eliteMember));
        System.out.println(classifyGeneration(groupClassMember));

        PremiumMember premiumMember = new PremiumMember("PRM1", 2000, "Coach Riya");
        premiumMember.attendSession();
        premiumMember.attendSession();
        premiumMember.attendSession();
        eliteMember.attendSession();
        eliteMember.attendSession();
        groupClassMember.attendSession();
        groupClassMember.attendSession();
        groupClassMember.attendSession();
        groupClassMember.attendSession();

        System.out.println(getTotalSessionsAttended(new GymMember[]{premiumMember, eliteMember, groupClassMember}));
    }
}
