package groovey.didactic.disco.org.didacticdisco.network;


import java.util.ArrayList;

public class LineRequest {

    private String id;

    private String nick;

    private ArrayList<Coordinate> coordinates;

    private ArrayList<Coordinate> bbox;

    private double thickness;

    private int color;

    public LineRequest(String id,
                       String nick,
                       ArrayList<Coordinate> coordinates,
                       ArrayList<Coordinate> bbox,
                       double thickness,
                       int color) {
        this.id = id;
        this.nick = nick;
        this.coordinates = coordinates;
        this.bbox = bbox;
        this.thickness = thickness;
        this.color = color;
    }
}
