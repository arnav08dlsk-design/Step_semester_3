package abstraction_interfaces.assignment_problems;

abstract class Drone {
    public Drone() {
    }

    public abstract String fly();
}

interface Trackable {
    String getLocation();
}

class DeliveryDrone extends Drone implements Trackable {
    private final String id;

    public DeliveryDrone(String id) {
        this.id = id;
    }

    @Override
    public String fly() {
        return id + " delivering a package";
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}

class ScoutDrone extends Drone {
    private final String id;

    public ScoutDrone(String id) {
        this.id = id;
    }

    @Override
    public String fly() {
        return id + " scouting the skyline";
    }
}

class GroundRobot implements Trackable {
    private final String id;

    public GroundRobot(String id) {
        this.id = id;
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}

public class A5_SkylineDeliveryFleet {
    static String getLocationIfTrackable(Object o) {
        if (o instanceof Trackable) {
            Trackable t = (Trackable) o;
            return t.getLocation();
        }
        return "Tracking not available";
    }

    public static void main(String[] args) {
        System.out.println(getLocationIfTrackable(new DeliveryDrone("DR-1")));
        System.out.println(getLocationIfTrackable(new ScoutDrone("SC-1")));
        System.out.println(getLocationIfTrackable(new GroundRobot("GR-1")));
    }
}
