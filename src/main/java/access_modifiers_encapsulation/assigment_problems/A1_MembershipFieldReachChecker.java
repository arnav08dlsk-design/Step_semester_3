import java.util.LinkedHashMap;
import java.util.Map;

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

        // Groups by modifier, not as one flat total. Every one of the four modifiers always
        // appears in the summary, even if it had zero attempts in this particular batch.
        static String summarizeByModifier(String[][] attempts) {
            String[] modifiers = {"private", "default", "protected", "public"};
            Map<String, int[]> counts = new LinkedHashMap<>();
            for (String m : modifiers) {
                counts.put(m, new int[]{0, 0}); // [allowed, denied]
            }

            for (String[] attempt : attempts) {
                String modifier = attempt[0];
                String result = classifyAccess(modifier, attempt[1]);
                int[] bucket = counts.get(modifier);
                if (result.equals("ALLOWED")) bucket[0]++;
                else bucket[1]++;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < modifiers.length; i++) {
                int[] bucket = counts.get(modifiers[i]);
                sb.append(modifiers[i]).append(": ").append(bucket[0]).append(" allowed / ")
                        .append(bucket[1]).append(" denied");
                if (i < modifiers.length - 1) sb.append(" | ");
            }
            return sb.toString();
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
