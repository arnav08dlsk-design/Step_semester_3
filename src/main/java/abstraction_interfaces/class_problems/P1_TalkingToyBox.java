package abstraction_interfaces.class_problems;

abstract class Toy {
    private static int counter = 1000;
    private final String toyId;
    protected final String name;

    protected Toy(String name) {
        this.name = name;
        this.toyId = "TOY-" + (++counter);
    }

    public abstract String makeSound();

    String getToyId() {
        return toyId;
    }
}

class ToyCar extends Toy {
    public ToyCar(String name) {
        super(name);
    }

    @Override
    public String makeSound() {
        return name + ": Vroom vroom!";
    }
}

class ToyRobot extends Toy {
    public ToyRobot(String name) {
        super(name);
    }

    @Override
    public String makeSound() {
        return name + ": Beep boop!";
    }
}

public class P1_TalkingToyBox {
    public static void main(String[] args) {
        // Toy t = new Toy("x");  // does not compile: Toy is abstract
        ToyCar c = new ToyCar("Speedster");
        ToyRobot r = new ToyRobot("Bolt");
        System.out.println(c.makeSound());
        System.out.println(r.makeSound());
        System.out.println(c.getToyId());
        System.out.println(r.getToyId());
    }
}
