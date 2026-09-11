public class A4_LibraryMemberJavaBean {

    static class LibraryMember {
        private String membershipId;
        private String name;
        private boolean premiumMember;
        private String securityAnswer; // one-way transformed, never retrievable
        private boolean membershipIdSet = false; // tracks the write-once guard

        public LibraryMember() {
        }

        public String getMembershipId() {
            return membershipId;
        }

        public void setMembershipId(String id) {
            // write-once: only the first call takes effect, later calls are silently ignored
            if (!membershipIdSet) {
                this.membershipId = id;
                this.membershipIdSet = true;
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPremiumMember() {
            return premiumMember;
        }

        public void setPremiumMember(boolean premium) {
            this.premiumMember = premium;
        }

        public void setSecurityAnswer(String answer) {
            // one-way, deterministic transform; no getter exists anywhere on the class
            this.securityAnswer = "HASH-" + Math.abs(answer.hashCode());
        }
    }

    public static void main(String[] args) {
        LibraryMember m = new LibraryMember();
        m.setMembershipId("LIB-8841");
        m.setName("Priya Nair");
        m.setPremiumMember(true);
        System.out.println(m.getMembershipId());

        m.setMembershipId("FAKE-0000"); // silently ignored, write-once
        System.out.println(m.getMembershipId());

        System.out.println(m.isPremiumMember());

        m.setSecurityAnswer("BlueMountain"); // no observable output
    }
}
