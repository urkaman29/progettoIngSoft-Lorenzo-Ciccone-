package shapes;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;

public final class ImageObject extends AbstractGraphicObject {
    private double factor = 0.5;
    private int id;
    private final Image image;

    private Point2D position;

    public Image getImage() {
        return image;
    }

    public ImageObject(ImageIcon img, Point2D pos) {
        position = new Point2D.Double(pos.getX(), pos.getY());
        image = img.getImage();
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
    public boolean contains(Point2D p) {
        double w = (factor * image.getWidth(null)) / 2;
        double h = (factor * image.getHeight(null)) / 2;
        double dx = Math.abs(p.getX() - position.getX());
        double dy = Math.abs(p.getY() - position.getY());
        return dx <= w && dy <= h;
    }

    @Override
    public void moveTo(Point2D p) {
        position.setLocation(p);
        notifyListeners(new GraphicEvent(this));
    }

    @Override
    public ImageObject clone() {
        ImageObject cloned = (ImageObject) super.clone();
        cloned.position = (Point2D) position.clone();
        return cloned;

    }

    @Override
    public Point2D getPosition() {

        return new Point2D.Double(position.getX(), position.getY());
    }

    @Override
    public void scale(double factor) {
        if (factor <= 0)
            throw new IllegalArgumentException();
        this.factor *= factor;
        notifyListeners(new GraphicEvent(this));
    }

    @Override
    public Dimension2D getDimension() {
        Dimension dim = new Dimension();
        dim.setSize(factor * image.getWidth(null),
                factor * image.getHeight(null));
        return dim;
    }

    @Override
    public String getType() {

        return "Image";
    }

    @Override
    public void setPosition(Point2D position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPosition'");
    }

    @Override
    public String toString() {
        Dimension2D dim = getDimension();
        return "ImageObject{" +
                "id=" + id +
                ", position=(" + position.getX() + ", " + position.getY() + ")" +
                ", scaleFactor=" + factor +
                ", dimensions=(" + dim.getWidth() + "x" + dim.getHeight() + ")" +
                '}';
    }

    @Override
    public double getArea() {
        double area = image.getWidth(null) * image.getHeight(null);
        return area;
    }

    @Override
    public double getPerimeter() {
        double perimeter = (image.getWidth(null) * 2) + (image.getHeight(null) * 2);
        return perimeter;
    }

}
