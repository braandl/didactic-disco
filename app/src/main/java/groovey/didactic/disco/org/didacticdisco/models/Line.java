package groovey.didactic.disco.org.didacticdisco.models;


import java.util.List;

import groovey.didactic.disco.org.didacticdisco.network.Coordinate;

public class Line {
    private List<Coordinate> line;
    private long timestamp;
    private String nick;

    public long getTimestamp() {
        return timestamp;
    }

    public List<Coordinate> getLine() {
        return line;
    }

    public String getNick() {
        return nick;
    }
}
