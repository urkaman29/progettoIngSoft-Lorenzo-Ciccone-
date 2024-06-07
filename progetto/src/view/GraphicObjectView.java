package view;

import java.awt.Graphics2D;

import shapes.GraphicObject;

public interface GraphicObjectView {
    void drawGraphicObject(GraphicObject go, Graphics2D g);
}
