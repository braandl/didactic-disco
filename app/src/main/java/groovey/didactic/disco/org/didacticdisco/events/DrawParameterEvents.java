package groovey.didactic.disco.org.didacticdisco.events;

import org.oscim.core.BoundingBox;
import org.oscim.core.osm.Bound;


public class DrawParameterEvents {


    private String color;
    private double thickness;
    private BoundingBox boundingBox;

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public DrawParameterEvents(BoundingBox bBox, String color, double thickness) {
        this.boundingBox = bBox;
        this.color = color;
        this.thickness = thickness;
    }

    public double getThickness() {
        return thickness;
    }

    public String getColor() {
        return color;
    }
}
