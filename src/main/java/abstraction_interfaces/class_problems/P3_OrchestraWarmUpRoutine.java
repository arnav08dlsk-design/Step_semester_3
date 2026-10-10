package abstraction_interfaces.class_problems;

abstract class Instrument {
    public Instrument() {
    }

    public abstract String play();
}

class StringInstrument extends Instrument {
    public StringInstrument() {
        super();
    }

    // Instrument.play() is abstract, so Java does not allow calling super.play() here.
    // This level provides the base message; Violin reuses it via super.play().
    @Override
    public String play() {
        return "Strumming the strings";
    }
}

class Violin extends StringInstrument {
    public Violin() {
        super();
    }

    @Override
    public String play() {
        return super.play() + ", with a bow drawn across four strings";
    }
}

public class P3_OrchestraWarmUpRoutine {
    public static void main(String[] args) {
        StringInstrument s = new StringInstrument();
        Violin v = new Violin();
        System.out.println(s.play());
        System.out.println(v.play());
    }
}
