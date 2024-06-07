package specificcommand;

import command.Command;
import composite.GraphicObjectManager;
import java.awt.geom.Point2D;
import java.util.*;

public class MoveCommand implements Command {
    private GraphicObjectManager manager;
    private int objectId;
    private Point2D newPosition;
    private Map<Integer, Point2D> oldPositions = new HashMap<>();

    public MoveCommand(GraphicObjectManager manager, int objectId, double x, double y) {
        this.manager = manager;
        this.objectId = objectId;
        this.newPosition = new Point2D.Double(x, y);
        this.oldPositions = manager.getObjectPositions(objectId);
    }

    @Override
    public boolean doIt() {
        return manager.moveObject(objectId, newPosition);
    }

    @Override
    public boolean undoIt() {
        if (oldPositions != null && !oldPositions.isEmpty()) {
            for (Map.Entry<Integer, Point2D> entry : oldPositions.entrySet()) {
                manager.moveObject(entry.getKey(), entry.getValue());
            }
            return true;
        }
        return false;
    }

}
