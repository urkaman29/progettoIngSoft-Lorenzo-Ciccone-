package specificcommand;

import command.Command;
import composite.GraphicObjectManager;

public class UngroupCommand implements Command {
    private GraphicObjectManager manager;
    private int groupId;

    public UngroupCommand(GraphicObjectManager manager, int groupId) {
        this.manager = manager;
        this.groupId = groupId;
    }

    @Override
    public boolean doIt() {
        return manager.removeGroup(groupId);
    }

    @Override
    public boolean undoIt() {
        return manager.recoverGroup(groupId);

    }
}
