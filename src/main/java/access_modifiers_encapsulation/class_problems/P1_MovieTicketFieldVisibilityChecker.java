public class P1_MovieTicketFieldVisibilityChecker {

    static class AccessChecker {
        static String classifyAccess(String fieldModifier, String accessorContext) {
            switch (fieldModifier) {
                case "private":
                    return accessorContext.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";
                case "default":
                case "protected":
                    return (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE"))
                            ? "ALLOWED" : "DENIED";
                case "public":
                    return "ALLOWED";
                default:
                    throw new IllegalArgumentException("Unknown modifier: " + fieldModifier);
            }
        }

        static String summarizeBatch(String[][] attempts) {
            int allowed = 0, denied = 0;
            for (String[] attempt : attempts) {
                if (classifyAccess(attempt[0], attempt[1]).equals("ALLOWED")) allowed++;
                else denied++;
            }
            return "Allowed: " + allowed + " | Denied: " + denied;
        }
    }

    // Field access levels chosen based on who genuinely needs to reach them
    static class MovieTicket {
        String seatNumber;          // default: same-package booking logic only
        String screenId;            // default: same-package booking logic only
        protected double ticketPrice; // protected: subclass (PremiumMovieTicket) needs it
        public String movieTitle;   // public: shown anywhere in the UI
    }

    public static void main(String[] args) {
        System.out.println(AccessChecker.classifyAccess("private", "SAME_CLASS"));
        System.out.println(AccessChecker.classifyAccess("protected", "DIFFERENT_PACKAGE"));

        String[][] attempts = {
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(AccessChecker.summarizeBatch(attempts));
    }
}
