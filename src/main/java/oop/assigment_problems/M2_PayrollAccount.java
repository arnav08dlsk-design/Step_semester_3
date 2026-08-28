public class M2_PayrollAccount {

    static class PayrollAccount {
        private double basicSalary;
        private double bonus;

        PayrollAccount(double openingBasicSalary) {
            if (openingBasicSalary < 0) {
                System.out.println("Warning: negative basic salary given. Starting at 0 instead.");
                this.basicSalary = 0;
            } else {
                this.basicSalary = openingBasicSalary;
            }
            this.bonus = 0;
        }

        void creditBonus(double amount) {
            if (amount <= 0) {
                System.out.println("Bonus amount must be positive. Credit rejected.");
                return;
            }
            bonus += amount;
            System.out.println("Bonus credited: Rs " + amount);
        }

        void deductTax(double percent) {
            if (percent < 0 || percent > 100) {
                System.out.println("Tax percent must be between 0 and 100. Deduction rejected.");
                return;
            }
            basicSalary -= basicSalary * (percent / 100);
            System.out.println("Tax deducted: " + (int) percent + "%");
        }

        double getNetSalary() {
            return basicSalary + bonus;
        }
    }

    public static void main(String[] args) {
        PayrollAccount account = new PayrollAccount(50000);
        account.creditBonus(5000);
        account.deductTax(10);
        System.out.println("Net salary: Rs " + account.getNetSalary());
    }
}
