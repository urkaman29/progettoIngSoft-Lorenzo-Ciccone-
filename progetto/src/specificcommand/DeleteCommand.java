package specificcommand;

import command.Command;
import composite.GraphicObjectManager;

public class DeleteCommand implements Command {

    private GraphicObjectManager manager;
    private int objectId;

    public DeleteCommand(GraphicObjectManager manager, int Id) {
        this.manager = manager;
        this.objectId = Id;
    }

    @Override
    public boolean doIt() {
        manager.removeGraphicObject(objectId);
        System.out.println("eliminato elemento con id: " + objectId);
        return true;
    }

    @Override
    public boolean undoIt() {
        return manager.recoverElement(objectId);
    }

}
