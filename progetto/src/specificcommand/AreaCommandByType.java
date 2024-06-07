package specificcommand;

import java.util.List;

import command.Command;
import composite.GraphicObjectManager;
import shapes.GraphicObject;

public class AreaCommandByType implements Command {

    private GraphicObjectManager manager;
    private String type;

    public AreaCommandByType(GraphicObjectManager manager, String type) {
        this.manager = manager;
        this.type = type;
    }

    @Override
    public boolean doIt() {
        List<GraphicObject> objectsByType = manager.listObjectsByTypePerimeter(type);
        double totalArea = objectsByType.stream()
                .mapToDouble(GraphicObject::getArea)
                .sum();

        System.out.println("Total Area of all " + type + " objects: " + totalArea);
        return true;
    }

    @Override
    public boolean undoIt() {
        // Questo comando non modifica lo stato, quindi non ha bisogno di
        // un'implementazione di undo
        return false;
    }
}
