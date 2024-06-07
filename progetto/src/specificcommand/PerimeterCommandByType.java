package specificcommand;

import java.util.List;
import command.Command;
import composite.GraphicObjectManager;
import shapes.GraphicObject;

public class PerimeterCommandByType implements Command {
    private GraphicObjectManager manager;
    private String type;

    public PerimeterCommandByType(GraphicObjectManager manager, String type) {
        this.manager = manager;
        this.type = type;
    }

    @Override
    public boolean doIt() {
        List<GraphicObject> objectsByType = manager.listObjectsByTypePerimeter(type);
        double totalPerimeter = objectsByType.stream()
                .mapToDouble(GraphicObject::getPerimeter)
                .sum();

        System.out.println("Total perimeter of all " + type + " objects: " + totalPerimeter);
        return true;
    }

    @Override
    public boolean undoIt() {
        // Questo comando non modifica lo stato, quindi non ha bisogno di
        // un'implementazione di undo
        return false;
    }
}
