package specificcommand;

import command.Command;
import composite.GraphicObjectManager;
import composite.GroupObject;
import shapes.GraphicObject;
import java.awt.geom.Point2D;
import java.util.*;

public class MoveOffsetCommand implements Command {
    private GraphicObjectManager manager;
    private int id;
    private double offsetX, offsetY;
    private Point2D oldPosition; // tiene traccia della posizione dell'elemento singolo
    private Map<Integer, Point2D> oldPositions = new HashMap<>(); // tiene traccia delle vecchie posizioni degli
                                                                  // elementi nei gruppi

    public MoveOffsetCommand(GraphicObjectManager manager, int objectId, double offsetX, double offsetY) {
        this.manager = manager;
        this.id = objectId;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public boolean doIt() {

        GraphicObject object = manager.getObject(id);
        if (object instanceof GroupObject) {
            GroupObject group = (GroupObject) object;
            for (GraphicObject member : group.getMembers()) {
                oldPositions.put(member.getId(), member.getPosition());
            }
        } else {
            oldPosition = object.getPosition();
        }

        return manager.moveObjectOffset(id, offsetX, offsetY);

    }

    @Override
    public boolean undoIt() {
        GraphicObject object = manager.getObject(id);
        if (object instanceof GroupObject) {
            GroupObject group = (GroupObject) object;
            for (GraphicObject member : group.getMembers()) {
                manager.moveObject(member.getId(), oldPositions.get(member.getId()));
            }
        } else {
            return manager.moveObject(id, oldPosition);
        }
        return true;

    }
}
