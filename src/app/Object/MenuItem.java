package app.Object;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private String id;
    private String name;
    private boolean serveHot;
    private double price;

    public MenuItem() {
        this.id = "";
        this.name = "";
        this.serveHot = true;
        this.price = 0.0;
    }

    public MenuItem(String id, String name, boolean serveHot, double price) {
        this.id = id;
        this.name = name;
        this.serveHot = serveHot;
        this.price = price;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getServeHot() {
        return this.serveHot;
    }

    public void setServeHot(boolean serveHot) {
        this.serveHot = serveHot;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
