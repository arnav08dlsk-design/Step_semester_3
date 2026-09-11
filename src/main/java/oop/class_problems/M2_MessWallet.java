public class M2_MessWallet {

    static class MessWallet {
        private double balance;

        MessWallet(double openingBalance) {
            if (openingBalance < 0) {
                System.out.println("Warning: negative opening balance given. Starting at 0 instead.");
                this.balance = 0;
            } else {
                this.balance = openingBalance;
            }
        }

        void topUp(double amount) {
            if (amount <= 0) {
                System.out.println("Top-up amount must be positive. Rejected.");
                return;
            }
            balance += amount;
        }

        void deduct(double amount) {
            if (amount > balance) {
                System.out.println("Deduct rejected: insufficient balance");
                return;
            }
            balance -= amount;
        }

        double getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) {
        MessWallet wallet = new MessWallet(500);
        wallet.topUp(200);
        System.out.println("Balance after top-up: " + wallet.getBalance());
        wallet.deduct(1000);
        System.out.println("Final balance: " + wallet.getBalance());
    }
}
