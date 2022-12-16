package cz.polacek.game.view.entity.player;

import cz.polacek.game.view.entity.Entity;

public class Bullet extends Entity {

    Face face;
    boolean destroyed;

    public Bullet(double x, double y, Face face) {
        this.x = x;
        this.y = y;
        this.face = face;
        this.destroyed = false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

}
