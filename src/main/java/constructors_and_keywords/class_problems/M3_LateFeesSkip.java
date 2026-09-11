public class M3_LateFeesSkip {

    static class Account {
        String regNo;
        double totalFee;

        Account(String regNo, double totalFee) {
            this.regNo = regNo;
            this.totalFee = totalFee;
        }

        final double calculateLateFee(int daysLate) {
            return totalFee * 0.01 * daysLate;
        }

        final void printSummary(int daysLate) {
            double fee = calculateLateFee(daysLate);
            System.out.println(regNo + " | Total Fee: Rs " + totalFee + " | Late Fee: Rs " + fee);
        }
    }

    public static void main(String[] args) {
        String[] regNos = {"RA001", "RA002", "RA003", "RA004"};
        double[] totalFees = {200000, 150000, 180000, 220000};
        int[] daysLate = {10, 0, -2, 5};

        for (int i = 0; i < regNos.length; i++) {
            Account acc = new Account(regNos[i], totalFees[i]);
            if (daysLate[i] > 0) {
                acc.printSummary(daysLate[i]);
            } else {
                System.out.println(regNos[i] + " - On time, no late fee");
            }
        }
    }
}
