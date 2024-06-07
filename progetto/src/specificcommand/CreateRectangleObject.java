package specificcommand;

import command.Command;
import composite.GraphicObjectManager;
import shapes.RectangleObject;
import view.GraphicObjectPanel;

import java.awt.geom.Point2D;

public class CreateRectangleObject implements Command {
    private GraphicObjectManager Storage;
    private RectangleObject rectangle;
    private double width;
    private double height;

    private Point2D position;
    private final GraphicObjectPanel panel;

    public CreateRectangleObject(GraphicObjectManager manager, GraphicObjectPanel gpanel, double width, double height,
            double x, double y) {
        this.Storage = manager;
        this.panel = gpanel;
        this.width = width;
        this.height = height;
        position = new Point2D.Double(x, y);
    }

    @Override
    public boolean doIt() {
        rectangle = new RectangleObject(width, height, position);
        Storage.addGraphicObject(rectangle);
        if (panel != null) {
            panel.add(rectangle);
            panel.repaint();

            return true;
        }
        return false;
    }

    @Override
    public boolean undoIt() {
        if (rectangle != null && panel != null) {
            Storage.removeGraphicObject(rectangle.getId());

            return true;
        }
        return false;
    }
}
