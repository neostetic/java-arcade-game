package cz.polacek.game.view.entity;

import cz.polacek.game.config.Config;

import java.awt.*;

public class Entity {
    public double x,y,xVel,yVel;

    public Rectangle getRect() {
        return new Rectangle((int) x, (int) y, Config.tileComputed, Config.tileComputed);
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

    public double getxVel() {
        return xVel;
    }

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }

    public double getyVel() {
        return yVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }
}
