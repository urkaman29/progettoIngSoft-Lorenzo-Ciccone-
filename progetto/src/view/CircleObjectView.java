package view;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import shapes.CircleObject;
import shapes.GraphicObject;

public class CircleObjectView implements GraphicObjectView {

    @Override
    public void drawGraphicObject(GraphicObject go, Graphics2D g) {
        CircleObject co = (CircleObject) go;
        Point2D position = co.getPosition();
        double r = co.getRadius();
        double x = position.getX() - r;
        double y = position.getY() - r;
        g.draw(new Ellipse2D.Double(x, y, r * 2.0, r * 2.0));
    }

}
