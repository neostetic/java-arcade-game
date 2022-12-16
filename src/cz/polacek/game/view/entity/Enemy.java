package cz.polacek.game.view.entity;

import cz.polacek.game.config.Config;
import cz.polacek.game.utils.SpritesheetUtils;
import cz.polacek.game.view.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {

    SpritesheetUtils spritesheetUtils = new SpritesheetUtils();

    Panel panel;
    BufferedImage[][] sprites;
    boolean destroyed;

    public Enemy(Panel panel, double x, double y, double xVel, double yVel) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.xVel = xVel;
        this.yVel = yVel;
        this.destroyed = false;
        sprites = spritesheetUtils.spritesheetToSprites("../assets/spritesheet.png");
    }

    public void getHit() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public void setVector(double xVector, double yVector) {
        xVel = xVector;
        yVel = yVector;
    }

    public void update() {
        x = x + xVel;
        y = y + yVel;
    }

    public void draw(Graphics2D graphics2D) {
        if (x > 0 || x < Config.windowWidth + Config.tileComputed || y > 0 || y < Config.windowHeight + Config.tileComputed) {
            graphics2D.drawImage(sprites[0][5], (int) x, (int) y, Config.tileComputed, Config.tileComputed, null);
        } else {
            destroyed = true;
        }
    }
}
