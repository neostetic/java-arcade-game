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

    public Enemy(Panel panel, double x, double y, double xVel, double yVel) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.xVel = xVel;
        this.yVel = yVel;
        sprites = spritesheetUtils.spritesheetToSprites("../assets/spritesheet.png");
    }

    public void update() {
        x = x + xVel;
        y = y + yVel;
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(sprites[0][5], (int) x, (int) y, Config.tileComputed, Config.tileComputed, null);
    }
}
