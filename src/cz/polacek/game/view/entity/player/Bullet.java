package cz.polacek.game.view.entity.player;

import cz.polacek.game.view.entity.Entity;

public class Bullet extends Entity {
    double x,y;
    Face face;

    public Bullet(double x, double y, Face face) {
        this.x = x;
        this.y = y;
        this.face = face;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }
}
