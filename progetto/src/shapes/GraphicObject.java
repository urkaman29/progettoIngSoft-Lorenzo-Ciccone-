package shapes;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public interface GraphicObject {

    void addGraphicObjectListener(GraphicObjectListener l);

    void removeGraphicObjectListener(GraphicObjectListener l);

    void moveTo(Point2D p);

    default void moveTo(double x, double y) {
        moveTo(new Point2D.Double(x, y));
    }

    void setPosition(Point2D position);

    Point2D getPosition();

    Dimension2D getDimension();

    void scale(double factor);

    boolean contains(Point2D p);

    String getType();

    void setId(int id);

    int getId();

    double getArea();

    double getPerimeter();

}
