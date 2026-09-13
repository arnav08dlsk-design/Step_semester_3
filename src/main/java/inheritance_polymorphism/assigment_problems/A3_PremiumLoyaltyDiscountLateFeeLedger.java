import java.util.Arrays;

public class A3_PremiumLoyaltyDiscountLateFeeLedger {

    static class GymMember {
        private String memberId;
        private int monthlyFee;
        private int[] lateFeeHistory = new int[10];
        private int feeCount = 0;

        public GymMember(String memberId, int monthlyFee) {
            if (memberId == null || memberId.trim().isEmpty() || memberId.length() < 4) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.memberId = memberId;
            this.monthlyFee = monthlyFee;
        }

        protected void chargeLateFee(int amount) {
            lateFeeHistory[feeCount] = amount;
            feeCount++;
        }

        int[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, feeCount); // defensive copy, every time
        }

        int getTotalLateFees() {
            int total = 0;
            for (int i = 0; i < feeCount; i++) total += lateFeeHistory[i];
            return total;
        }
    }

    static class PremiumMember extends GymMember {
        private String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }

        @Override
        protected void chargeLateFee(int amount) {
            super.chargeLateFee(amount / 2); // reuse parent's deduction + recording logic
        }
    }

    public static void main(String[] args) {
        PremiumMember p = new PremiumMember("MEM5", 2000, "Coach Riya");
        p.chargeLateFee(200);
        System.out.println(p.getTotalLateFees());

        int[] history = p.getLateFeeHistory();
        history[0] = 999; // tampering with the returned copy
        System.out.println(Arrays.toString(p.getLateFeeHistory()));
    }
}
