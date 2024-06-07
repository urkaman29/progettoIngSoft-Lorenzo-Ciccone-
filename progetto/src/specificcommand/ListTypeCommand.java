package specificcommand;

import command.Command;
import composite.GraphicObjectManager;

public class ListTypeCommand implements Command {
    private GraphicObjectManager manager;
    private String type;

    public ListTypeCommand(GraphicObjectManager manager, String type) {
        this.manager = manager;
        this.type = type;
    }

    @Override
    public boolean doIt() {
        String result = manager.listObjectsByType(type);
        System.out.println(result);
        return true;
    }

    @Override
    public boolean undoIt() {
        // Non applicabile per i comandi di listaggio
        return false;
    }
}
