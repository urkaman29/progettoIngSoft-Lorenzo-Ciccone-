package specificcommand;

import command.Command;
import composite.GraphicObjectManager;

public class ListAllCommand implements Command {
    private GraphicObjectManager manager;

    public ListAllCommand(GraphicObjectManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean doIt() {
        String result = manager.listAllObjects();
        System.out.println(result);
        return true;
    }

    @Override
    public boolean undoIt() {
        // Non applicabile per i comandi di listaggio
        return false;
    }
}
