package abstraction_interfaces.assignment_problems;

abstract class GardenTool {
    public GardenTool() {
    }

    public abstract String use();
}

class CuttingTool extends GardenTool {
    public CuttingTool() {
        super();
    }

    // GardenTool.use() is abstract, so Java does not allow calling super.use() here.
    // This level supplies the base message; Pruner reuses it via super.use().
    @Override
    public String use() {
        return "Using the tool in the garden, blade sharpened first";
    }
}

class Pruner extends CuttingTool {
    public Pruner() {
        super();
    }

    @Override
    public String use() {
        return super.use() + ", then trimming branches precisely";
    }
}

public class A3_BackyardToolshedRoutine {
    public static void main(String[] args) {
        CuttingTool c = new CuttingTool();
        Pruner p = new Pruner();
        System.out.println(c.use());
        System.out.println(p.use());
    }
}
