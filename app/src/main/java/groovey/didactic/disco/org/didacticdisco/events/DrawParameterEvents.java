package groovey.didactic.disco.org.didacticdisco.events;

import org.oscim.core.BoundingBox;
import org.oscim.core.osm.Bound;


public class DrawParameterEvents {


    private int color;
    private double thickness;
    private BoundingBox boundingBox;

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public DrawParameterEvents(BoundingBox bBox, int color, double thickness) {
        this.boundingBox = bBox;
        this.color = color;
        this.thickness = thickness;
    }

    public double getThickness() {
        return thickness;
    }

    public int getColor() {
        return color;
    }
}
