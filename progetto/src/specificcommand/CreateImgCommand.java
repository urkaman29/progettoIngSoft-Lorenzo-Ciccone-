package specificcommand;

import command.Command;
import composite.GraphicObjectManager;
import shapes.ImageObject;
import view.GraphicObjectPanel;
import javax.swing.ImageIcon;

import java.awt.geom.Point2D;

public class CreateImgCommand implements Command {

    private final double Xpos;
    private final double Ypos;
    private final Point2D position;
    private ImageObject img;

    private String imgName;
    private final GraphicObjectPanel gpanel;
    private GraphicObjectManager Storage;

    public CreateImgCommand(String Name, double x, double y, GraphicObjectPanel panel, GraphicObjectManager manager) {
        imgName = Name;
        Xpos = x;
        Ypos = y;
        position = new Point2D.Double(Xpos, Ypos);
        this.gpanel = panel;
        this.Storage = manager;
    }

    @Override
    public boolean doIt() {

        ImageIcon icon = new ImageIcon(imgName);
        img = new ImageObject(icon, position);
        Storage.addGraphicObject(img);

        if (gpanel != null) {
            gpanel.add(img);
            gpanel.repaint();
            return true;
        }
        return false;
    }

    @Override
    public boolean undoIt() {
        if (img != null && gpanel != null) {
            Storage.removeGraphicObject(img.getId());

            return true;
        }
        return false;
    }

}
