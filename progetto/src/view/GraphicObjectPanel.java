package view;

import shapes.GraphicEvent;
import shapes.GraphicObject;
import shapes.GraphicObjectListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

public class GraphicObjectPanel extends JComponent implements GraphicObjectListener {

    private static final long serialVersionUID = 8993548105090978185L;

    private final List<GraphicObject> objects = new LinkedList<>();

    public GraphicObjectPanel() {
        setBackground(Color.WHITE);
    }

    @Override
    public void graphicChanged(GraphicEvent e) {
        repaint();
        revalidate();
    }

    public GraphicObject getGraphicObjectAt(Point2D p) {
        for (GraphicObject g : objects) {
            if (g.contains(p))
                return g;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        for (GraphicObject go : objects) {
            GraphicObjectView view = GraphicObjectViewFactory.FACTORY.createView(go);
            view.drawGraphicObject(go, g2);
        }

    }

    public void add(GraphicObject go) {
        objects.add(go);
        go.addGraphicObjectListener(this);
        repaint();
    }

    public void remove(GraphicObject go) {
        if (objects.remove(go)) {
            repaint();
            go.removeGraphicObjectListener(this);
        }

    }

}