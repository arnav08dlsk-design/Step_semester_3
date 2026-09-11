public class P4_MovieBookingProfileJavaBean {

    static class MovieBookingProfile {
        private String name;
        private boolean confirmed;
        private String otp; // stores only a one-way transformed value

        public MovieBookingProfile() {
        }

        public MovieBookingProfile(String name) {
            this();
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isConfirmed() {
            return confirmed;
        }

        public void setConfirmed(boolean confirmed) {
            this.confirmed = confirmed;
        }

        public void setOtp(String otp) {
            // one-way, deterministic transform; no getter exists anywhere on the class
            this.otp = "HASH-" + Math.abs(otp.hashCode());
        }
    }

    public static void main(String[] args) {
        System.out.println(new MovieBookingProfile("Rahul Dev").getName());

        MovieBookingProfile p = new MovieBookingProfile("Rahul Dev");
        p.setConfirmed(true);
        System.out.println(p.isConfirmed());

        p.setOtp("4471"); // no observable output; nothing can retrieve it again
    }
}
