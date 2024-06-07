package specificcommand;

import command.Command;
import composite.GraphicObjectManager;

public class ListObjectCommand implements Command {
    private GraphicObjectManager manager;
    private int objectId;

    public ListObjectCommand(GraphicObjectManager manager, int objectId) {
        this.manager = manager;
        this.objectId = objectId;
    }

    @Override
    public boolean doIt() {
        String result = manager.listObjectDetails(objectId);
        System.out.println(result);
        return true;
    }

    @Override
    public boolean undoIt() {
        // Non applicabile per i comandi di listaggio
        return false;
    }
}
