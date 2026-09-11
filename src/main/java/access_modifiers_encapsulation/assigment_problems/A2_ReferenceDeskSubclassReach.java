public class A2_ReferenceDeskSubclassReach {

    static class AccessChecker {
        static String classifyAccess(String fieldModifier, String accessorContext) {
            switch (fieldModifier) {
                case "private":
                    return accessorContext.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";
                case "default":
                    return (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE"))
                            ? "ALLOWED" : "DENIED";
                case "protected":
                    if (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE")) {
                        return "ALLOWED";
                    }
                    if (accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                        return "ALLOWED";
                    }
                    return "DENIED"; // includes DIFFERENT_PACKAGE and ..._PARENT_TYPE
                case "public":
                    return "ALLOWED";
                default:
                    throw new IllegalArgumentException("Unknown modifier: " + fieldModifier);
            }
        }

        // Scans strictly in order and stops at the first denied attempt — true early exit,
        // not a batch count searched afterward.
        static String firstDeniedAttempt(String[][] attempts) {
            for (int i = 0; i < attempts.length; i++) {
                String modifier = attempts[i][0];
                String context = attempts[i][1];
                if (classifyAccess(modifier, context).equals("DENIED")) {
                    return modifier + " via " + context + " (attempt #" + (i + 1) + ")";
                }
            }
            return "None Denied";
        }
    }

    public static void main(String[] args) {
        String[][] attempts1 = {
                {"public", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"},
                {"protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"},
                {"protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"}
        };
        System.out.println(AccessChecker.firstDeniedAttempt(attempts1));

        String[][] attempts2 = {
                {"public", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"},
                {"protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"}
        };
        System.out.println(AccessChecker.firstDeniedAttempt(attempts2));
    }
}
