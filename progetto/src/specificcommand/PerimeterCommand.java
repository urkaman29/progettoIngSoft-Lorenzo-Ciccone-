package specificcommand;

import command.Command;
import composite.GraphicObjectManager;

public class PerimeterCommand implements Command {

    private GraphicObjectManager manager;
    private int objectId;
    private double result;

    public PerimeterCommand(GraphicObjectManager manager, int objectId) {
        this.manager = manager;
        this.objectId = objectId;
    }

    @Override
    public boolean doIt() {
        result = manager.calculatePerimeter(objectId);
        System.out.println("The perimeter of the object/group with ID " + objectId + " is: " + result);
        return true;
    }

    @Override
    public boolean undoIt() {
        // L'operazione di calcolo del perimetro non richiede un'azione di undo.
        return false;
    }

    public double getResult() {
        return result;
    }

}
