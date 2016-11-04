package groovey.didactic.disco.org.didacticdisco.models;

/**
 * Created by sbrandt on 04.11.16.
 */

public class Fields {

    private double latstart;
    private double lonstart;
    private double latend;
    private double lonend;
    private double thickness;
    private int color;
    private long timestamp;

    public double getLatstart() {
        return latstart;
    }

    public void setLatstart(double latstart) {
        this.latstart = latstart;
    }

    public double getLonstart() {
        return lonstart;
    }

    public void setLonstart(double lonstart) {
        this.lonstart = lonstart;
    }

    public double getLatend() {
        return latend;
    }

    public void setLatend(double latend) {
        this.latend = latend;
    }

    public double getLonend() {
        return lonend;
    }

    public void setLonend(double lonend) {
        this.lonend = lonend;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
