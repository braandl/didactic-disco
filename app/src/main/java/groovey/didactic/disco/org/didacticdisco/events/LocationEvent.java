package groovey.didactic.disco.org.didacticdisco.events;

import android.location.Location;

public class LocationEvent {

    private Location location;

    public LocationEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

}
