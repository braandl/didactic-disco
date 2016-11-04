package groovey.didactic.disco.org.didacticdisco.models;


import java.util.List;

import groovey.didactic.disco.org.didacticdisco.network.Coordinate;

public class Line {
    private List<Coordinate> line;
    private long timestamp;
    private String nick;
    private int color;
    private double thickness;

    public long getTimestamp() {
        return timestamp;
    }

    public List<Coordinate> getLine() {
        return line;
    }

    public String getNick() {
        return nick;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }
}
