package view;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import shapes.GraphicObject;
import shapes.RectangleObject;

public class RectangleObjectView implements GraphicObjectView {

    @Override
    public void drawGraphicObject(GraphicObject go, Graphics2D g) {
        if (go instanceof RectangleObject) {
            RectangleObject rect = (RectangleObject) go;

            double x = rect.getPosition().getX();
            double y = rect.getPosition().getY();
            double width = rect.getWidth();
            double height = rect.getHeight();

            g.draw(new Rectangle2D.Double(x, y, width, height));
        }
    }
}
