package shapes;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class CircleObject extends AbstractGraphicObject {
    private Point2D position;
    private int id;
    private double radius;

    public CircleObject(double r, Point2D pos) {
        if (r <= 0)
            throw new IllegalArgumentException();
        position = new Point2D.Double(pos.getX(), pos.getY());
        radius = r;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void moveTo(Point2D p) {
        position.setLocation(p);
        notifyListeners(new GraphicEvent(this));
    }

    @Override
    public Point2D getPosition() {

        return new Point2D.Double(position.getX(), position.getY());
    }

    @Override
    public void scale(double factor) {
        radius *= factor;
        notifyListeners(new GraphicEvent(this));
    }

    @Override
    public Dimension2D getDimension() {
        Dimension d = new Dimension();
        d.setSize(2 * radius, 2 * radius);

        return d;
    }

    @Override
    public boolean contains(Point2D p) {
        return (position.distance(p) <= radius);

    }

    @Override
    public CircleObject clone() {
        CircleObject cloned = (CircleObject) super.clone();
        cloned.position = (Point2D) position.clone();
        return cloned;
    }

    @Override
    public String getType() {

        return "Circle";
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void setPosition(Point2D position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPosition'");
    }

    @Override
    public String toString() {
        return "CircleObject{" +
                "id=" + id +
                ", position=(" + position.getX() + ", " + position.getY() + ")" +
                ", radius=" + radius +
                '}';
    }

    @Override
    public double getArea() {
        double area = (radius * radius) * 3.14;
        return area;
    }

    @Override
    public double getPerimeter() {
        double perimetro = (radius * 2) * 3.14;
        return perimetro;
    }

}
