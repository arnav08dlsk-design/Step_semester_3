public class M5_AccountBatchPayments {

    static class FeeAccount {
        String accountId;

        FeeAccount(String accountId) {
            this.accountId = accountId;
        }
    }

    static class HostelFeeAccount extends FeeAccount {
        HostelFeeAccount(String accountId) {
            super(accountId);
        }
    }

    static int hostelCount = 0;
    static int dayScholarCount = 0;

    static void processPayment(FeeAccount account, double amount) {
        if (account instanceof HostelFeeAccount) {
            System.out.println("Paid in two installments (hostel account)");
            hostelCount++;
        } else {
            System.out.println("Paid in one go (day-scholar account)");
            dayScholarCount++;
        }
    }

    public static void main(String[] args) {
        FeeAccount[] accounts = {
                new HostelFeeAccount("H1"),
                new HostelFeeAccount("H2"),
                new FeeAccount("F1"),
                new FeeAccount("F2")
        };

        for (FeeAccount acc : accounts) {
            processPayment(acc, 60000);
        }

        System.out.println("Hostel accounts processed: " + hostelCount + " | Day-scholar accounts processed: " + dayScholarCount);
    }
}
