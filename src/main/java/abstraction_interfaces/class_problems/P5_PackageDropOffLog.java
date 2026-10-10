package abstraction_interfaces.class_problems;

abstract class DeliveryNote {
    public DeliveryNote() {
    }

    public abstract String confirmDelivery();

    // Overloaded: same name, different parameter list (compile-time polymorphism)
    String confirmDelivery(String signature) {
        return confirmDelivery() + ", signed by " + signature;
    }
}

class ParcelNote extends DeliveryNote {
    private final String trackingId;

    public ParcelNote(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String confirmDelivery() {
        return "Parcel " + trackingId + " delivered";
    }
}

class LetterNote extends DeliveryNote {
    private final String trackingId;

    public LetterNote(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String confirmDelivery() {
        return "Letter " + trackingId + " delivered";
    }
}

public class P5_PackageDropOffLog {
    static void logAll(DeliveryNote[] notes) {
        for (DeliveryNote note : notes) {
            System.out.println(note.confirmDelivery());
        }
    }

    public static void main(String[] args) {
        ParcelNote p = new ParcelNote("TRK-1");
        System.out.println(p.confirmDelivery());
        System.out.println(p.confirmDelivery("J. Smith"));

        DeliveryNote ref = p; // upcasting
        logAll(new DeliveryNote[]{ref, new LetterNote("TRK-2")});
    }
}
