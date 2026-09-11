public class C5_BankTransactionReferenceGenerator {

    static String normalizeReference(String raw) {
        String trimmed = raw.trim();
        String bankCode = trimmed.substring(0, 3).toUpperCase();
        String rest = trimmed.substring(3);
        return bankCode + rest;
    }

    static String validateAndFormat(String reference) {
        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }
        String bankCode = reference.substring(0, 3);
        String body = reference.substring(3);

        for (int i = 0; i < bankCode.length(); i++) {
            if (!Character.isLetter(bankCode.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }
        for (int i = 0; i < body.length(); i++) {
            if (!Character.isDigit(body.charAt(i))) {
                return "Invalid: reference body must be digits";
            }
        }

        String datePart = body.substring(0, 6);
        String seqPart = body.substring(6);
        String dd = datePart.substring(0, 2);
        String mm = datePart.substring(2, 4);
        String yy = datePart.substring(4, 6);

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(bankCode).append("] DATE: ").append(dd).append("/").append(mm).append("/").append(yy)
                .append(" | SEQ: ").append(seqPart);
        return sb.toString();
    }

    public static void main(String[] args) {
        String norm1 = normalizeReference(" hdf03022600042 ");
        System.out.println(validateAndFormat(norm1));

        String raw2 = "12F03022600042";
        System.out.println(validateAndFormat(raw2));
    }
}
