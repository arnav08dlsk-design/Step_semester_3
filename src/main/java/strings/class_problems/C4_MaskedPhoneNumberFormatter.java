public class C4_MaskedPhoneNumberFormatter {

    static String maskPhoneNumber(String phone) {
        if (phone.length() != 10 || !phone.chars().allMatch(Character::isDigit)) {
            return "Invalid phone number";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("XXXXXX");
        sb.append("-");
        sb.append(phone.substring(6));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(maskPhoneNumber("9876543210"));
        System.out.println(maskPhoneNumber("98765"));
    }
}
