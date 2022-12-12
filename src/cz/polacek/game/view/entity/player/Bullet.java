package cz.polacek.game.view.entity.player;

import cz.polacek.game.view.entity.Entity;

public class Bullet extends Entity {

    Face face;

    public Bullet(double x, double y, Face face) {
        this.x = x;
        this.y = y;
        this.face = face;
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

}
