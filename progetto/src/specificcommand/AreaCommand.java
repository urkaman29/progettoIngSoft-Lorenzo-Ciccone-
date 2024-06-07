package specificcommand;

import command.Command;
import composite.GraphicObjectManager;

public class AreaCommand implements Command {
    private GraphicObjectManager manager;
    private int objectId;
    private double result;

    public AreaCommand(GraphicObjectManager manager, int objectId) {
        this.manager = manager;
        this.objectId = objectId;
    }

    @Override
    public boolean doIt() {
        result = manager.calculateArea(objectId);
        System.out.println("The area of the object/group with ID " + objectId + " is: " + result);
        return true;
    }

    @Override
    public boolean undoIt() {
        // L'operazione di calcolo dell'area non richiede un'azione di undo.
        return false;
    }

    public double getResult() {
        return result;
    }
}
