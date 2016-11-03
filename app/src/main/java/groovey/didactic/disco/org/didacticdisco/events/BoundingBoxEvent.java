package groovey.didactic.disco.org.didacticdisco.events;

import org.oscim.core.BoundingBox;
import org.oscim.core.osm.Bound;

/**
 * Created by sbrandt on 03.11.16.
 */

public class BoundingBoxEvent {

    private BoundingBox boundingBox;

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public BoundingBoxEvent(BoundingBox bBox) {
        this.boundingBox = bBox;
    }
}
