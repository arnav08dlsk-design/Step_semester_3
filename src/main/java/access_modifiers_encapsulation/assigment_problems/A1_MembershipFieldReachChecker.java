public class A1_MembershipFieldReachChecker {

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

        // Groups by modifier, not as one flat total — using plain counters and arrays,
        // the same tools this course has used all along, instead of a Map.
        // Every one of the four modifiers always appears in the summary, even if it had
        // zero attempts in this particular batch.
        static String summarizeByModifier(String[][] attempts) {
            int privateAllowed = 0, privateDenied = 0;
            int defaultAllowed = 0, defaultDenied = 0;
            int protectedAllowed = 0, protectedDenied = 0;
            int publicAllowed = 0, publicDenied = 0;

            for (String[] attempt : attempts) {
                String modifier = attempt[0];
                boolean allowed = classifyAccess(modifier, attempt[1]).equals("ALLOWED");

                switch (modifier) {
                    case "private":
                        if (allowed) privateAllowed++; else privateDenied++;
                        break;
                    case "default":
                        if (allowed) defaultAllowed++; else defaultDenied++;
                        break;
                    case "protected":
                        if (allowed) protectedAllowed++; else protectedDenied++;
                        break;
                    case "public":
                        if (allowed) publicAllowed++; else publicDenied++;
                        break;
                }
            }

            return "private: " + privateAllowed + " allowed / " + privateDenied + " denied | "
                    + "default: " + defaultAllowed + " allowed / " + defaultDenied + " denied | "
                    + "protected: " + protectedAllowed + " allowed / " + protectedDenied + " denied | "
                    + "public: " + publicAllowed + " allowed / " + publicDenied + " denied";
        }
    }

    static class LibraryMember {
        private String membershipPin;      // inaccessible outside LibraryMember itself
        String branchCode;                 // default: same package only
        protected double finesOwed;        // same package + subclass reach (see Problem 2)
        public String displayName;         // reachable from anywhere
    }

    public static void main(String[] args) {
        System.out.println(AccessChecker.classifyAccess("private", "SAME_CLASS"));
        System.out.println(AccessChecker.classifyAccess("protected", "DIFFERENT_PACKAGE"));

        String[][] attempts = {
                {"private", "SAME_CLASS"},
                {"private", "SAME_PACKAGE"},
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"protected", "SAME_PACKAGE"},
                {"protected", "SAME_CLASS"},
                {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(AccessChecker.summarizeByModifier(attempts));
    }
}
