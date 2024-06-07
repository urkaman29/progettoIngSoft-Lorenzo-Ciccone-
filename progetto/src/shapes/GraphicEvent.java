package shapes;

public class GraphicEvent {

    private final GraphicObject source;

    public GraphicEvent(GraphicObject src) {
        source = src;
    }

    public GraphicObject getSource() {
        return source;
    }
}
