package shapes;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class RectangleObject extends AbstractGraphicObject {
    private Point2D position;
    private double width;
    private double height;
    private int id;

    public RectangleObject(double width, double height, Point2D position) {
        this.width = width;
        this.height = height;
        this.position = position;
    }

    @Override
    public void moveTo(Point2D p) {
        this.position = p;
        notifyListeners(new GraphicEvent(this));
    }

    @Override
    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public Dimension2D getDimension() {
        return new Dimension2D() {
            @Override
            public void setSize(double w, double h) {
                width = w;
                height = h;
            }

            @Override
            public double getWidth() {
                return width;
            }

            @Override
            public double getHeight() {
                return height;
            }
        };
    }

    @Override
    public void scale(double factor) {
        width *= factor;
        height *= factor;
        notifyListeners(new GraphicEvent(this));
    }

    @Override
    public boolean contains(Point2D p) {
        return new Rectangle2D.Double(position.getX(), position.getY(), width, height).contains(p);
    }

    @Override
    public String getType() {
        return "Rectangle";
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public RectangleObject clone() {
        RectangleObject cloned = (RectangleObject) super.clone();
        cloned.position = (Point2D) position.clone();
        return cloned;
    }

    @Override
    public String toString() {
        return "RectangleObject{" +
                "id=" + id +
                ", position=(" + position.getX() + ", " + position.getY() + ")" +
                '}';
    }

    @Override
    public double getPerimeter() {
        double perimeter = (width * 2) + (height * 2);
        return perimeter;
    }
}
