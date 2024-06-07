package specificcommand;

import command.Command;
import composite.GraphicObjectManager;
import java.util.List;

public class GroupCommand implements Command {
    private GraphicObjectManager manager;
    private List<Integer> objectIds;
    private int lastCreatedGroupId = -1;

    public GroupCommand(GraphicObjectManager manager, List<Integer> objectIds) {
        this.manager = manager;
        this.objectIds = objectIds; // L'assegnazione diretta, nessuna conversione necessaria
    }

    @Override
    public boolean doIt() {
        int groupId = manager.createGroup(objectIds);
        if (groupId != -1) {
            System.out.println("Group created with ID: " + groupId);
            lastCreatedGroupId = groupId;
            return true;
        }
        return false;
    }

    @Override
    public boolean undoIt() {
        if (lastCreatedGroupId != -1) {
            boolean success = manager.removeGroup(lastCreatedGroupId);
            if (success) {
                System.out.println("Group with ID: " + lastCreatedGroupId + " has been undone (removed).");
                return true;
            } else {
                System.out.println("Failed to undo the group with ID: " + lastCreatedGroupId);
                return false;
            }
        }
        return false;
    }

}
