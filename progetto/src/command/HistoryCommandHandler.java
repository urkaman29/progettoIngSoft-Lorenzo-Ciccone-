package command;

import java.util.LinkedList;

import view.GraphicObjectPanel;

public class HistoryCommandHandler implements CommandHandler {
    private int maxHistoryLength = 100;

    private final LinkedList<Command> history = new LinkedList<>();

    private final LinkedList<Command> redoList = new LinkedList<>();

    GraphicObjectPanel gpanel;

    public HistoryCommandHandler(GraphicObjectPanel panel) {
        this(100);
        this.gpanel = panel;
    }

    public HistoryCommandHandler(int maxHistoryLength) {

        if (maxHistoryLength < 0)
            throw new IllegalArgumentException();
        this.maxHistoryLength = maxHistoryLength;
    }

    public void handle(Command cmd) {

        if (cmd.doIt()) {
            addToHistory(cmd);
        } else {
            history.clear();
        }
        if (redoList.size() > 0)
            redoList.clear();
    }

    public void redo() {
        if (redoList.size() > 0) {
            Command redoCmd = redoList.removeFirst();
            redoCmd.doIt();
            gpanel.repaint();
            history.addFirst(redoCmd);

        }
    }

    public void undo() {
        if (history.size() > 0) {
            Command undoCmd = history.removeFirst();
            undoCmd.undoIt();
            gpanel.repaint();
            redoList.addFirst(undoCmd);
        }
    }

    private void addToHistory(Command cmd) {
        history.addFirst(cmd);
        if (history.size() > maxHistoryLength) {
            history.removeLast();
        }

    }
}
