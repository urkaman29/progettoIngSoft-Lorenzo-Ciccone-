package composite;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import shapes.AbstractGraphicObject;
import shapes.GraphicObject;

public class GroupObject extends AbstractGraphicObject {
    private List<GraphicObject> members = new ArrayList<>();
    private int id;

    public GroupObject(int id) {
        this.id = id;
    }

    public void addMember(GraphicObject obj) {
        members.add(obj);
    }

    public List<GraphicObject> getMembers() {
        return new ArrayList<>(members); // Ritorno una copia per prevenire modifiche esterne
    }

    @Override
    public void moveTo(Point2D p) {
        for (GraphicObject member : members) {
            member.moveTo(p);
        }
    }

    @Override
    public void scale(double factor) {
        for (GraphicObject member : members) {
            member.scale(factor);
        }
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Point2D getPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPosition'");
    }

    @Override
    public Dimension2D getDimension() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDimension'");
    }

    @Override
    public boolean contains(Point2D p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    @Override
    public void setPosition(Point2D position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPosition'");
    }

    @Override
    public double getArea() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getArea'");
    }

    @Override
    public double getPerimeter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPerimeter'");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GroupObject ID: " + id + " with members:\n");
        for (GraphicObject member : members) {
            sb.append(member.toString()).append("\n");
        }
        return sb.toString();
    }

}
