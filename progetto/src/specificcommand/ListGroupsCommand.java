package specificcommand;

import command.Command;
import composite.GraphicObjectManager;

public class ListGroupsCommand implements Command {
    private GraphicObjectManager manager;

    public ListGroupsCommand(GraphicObjectManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean doIt() {
        String result = manager.listAllGroups();
        System.out.println(result);
        return true;
    }

    @Override
    public boolean undoIt() {
        // Non applicabile per i comandi di listaggio
        return false;
    }
}
