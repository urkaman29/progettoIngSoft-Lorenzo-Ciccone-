package specificcommand;

import command.Command;
import composite.GraphicObjectManager;
import shapes.CircleObject;
import view.GraphicObjectPanel;
import java.awt.geom.Point2D;

public class CreateCircleCommand implements Command {

    private final double raggio;
    private final double Xpos;
    private final double Ypos;
    private final Point2D position;
    private CircleObject circle;

    private final GraphicObjectPanel gpanel;
    private GraphicObjectManager Storage;

    public CreateCircleCommand(double r, double x, double y, GraphicObjectPanel gpanel, GraphicObjectManager manager) {
        raggio = r;
        Xpos = x;
        Ypos = y;
        position = new Point2D.Double(Xpos, Ypos);
        this.gpanel = gpanel;
        this.Storage = manager;
    }

    @Override
    public boolean doIt() {

        circle = new CircleObject(raggio, position);
        Storage.addGraphicObject(circle);

        if (gpanel != null) {
            gpanel.add(circle);
            gpanel.repaint();

            return true;
        }
        return false;
    }

    @Override
    public boolean undoIt() {
        if (circle != null && gpanel != null) {
            Storage.removeGraphicObject(circle.getId());

            return true;
        }
        return false;
    }

    public CircleObject getCircle() {
        return circle;
    }

}
