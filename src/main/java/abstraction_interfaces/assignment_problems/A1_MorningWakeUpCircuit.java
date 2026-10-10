package abstraction_interfaces.assignment_problems;

interface Ringable {
    String ring();
}

class AlarmClock implements Ringable {
    private final String time;

    public AlarmClock(String time) {
        this.time = time;
    }

    @Override
    public String ring() {
        return "Alarm ringing for " + time;
    }
}

class Doorbell implements Ringable {
    private final String location;

    public Doorbell(String location) {
        this.location = location;
    }

    @Override
    public String ring() {
        return "Doorbell ringing at " + location;
    }
}

public class A1_MorningWakeUpCircuit {
    static void ringAll(Ringable[] devices) {
        for (Ringable device : devices) {
            System.out.println(device.ring());
        }
    }

    public static void main(String[] args) {
        AlarmClock a = new AlarmClock("7:00 AM");
        Doorbell d = new Doorbell("Front Door");
        ringAll(new Ringable[]{a, d});
    }
}
