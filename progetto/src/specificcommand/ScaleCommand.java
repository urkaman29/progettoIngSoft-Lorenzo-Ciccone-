package specificcommand;

import command.Command;
import composite.GraphicObjectManager;
import shapes.GraphicObject;

public class ScaleCommand implements Command {
    private GraphicObjectManager manager;
    private int objectId;
    private double scaleFactor;

    public ScaleCommand(GraphicObjectManager manager, int objectId, double scaleFactor) {
        this.manager = manager;
        this.objectId = objectId;
        this.scaleFactor = scaleFactor;
    }

    @Override
    public boolean doIt() {
        GraphicObject object = manager.getObject(objectId);
        if (object != null) {
            object.scale(scaleFactor);
            System.out.println("Scaled object with ID " + objectId + " by factor of " + scaleFactor);
            return true;
        }
        System.out.println("No object found with ID " + objectId);
        return false;
    }

    @Override
    public boolean undoIt() {
        GraphicObject object = manager.getObject(objectId);
        if (object != null) {
            object.scale(1 / scaleFactor);
            System.out.println("Undo scaling of object with ID " + objectId + " by factor of " + scaleFactor);
            return true;
        }
        return false;
    }
}
