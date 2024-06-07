package view;

import java.util.HashMap;
import java.util.Map;

import shapes.GraphicObject;

public enum GraphicObjectViewFactory {

    FACTORY;

    private final Map<Class<? extends GraphicObject>, GraphicObjectView> viewMap = new HashMap<>();

    GraphicObjectView createView(GraphicObject go) {
        return viewMap.get(go.getClass());
    }

    public void installView(Class<? extends GraphicObject> clazz, GraphicObjectView view) {
        viewMap.put(clazz, view);
    }
}
