package cz.polacek.game.view.entity;

import cz.polacek.game.config.Config;
import cz.polacek.game.utils.SpritesheetUtils;
import cz.polacek.game.view.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Powerup extends Entity {

    SpritesheetUtils spritesheetUtils = new SpritesheetUtils();

    Panel panel;
    PowerupType powerupType;
    int powerupTypeInt;
    BufferedImage[][] sprites;

    public Powerup(Panel panel, double x, double y, PowerupType powerupType) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.powerupType = powerupType;
        this.powerupTypeInt = powerupType.ordinal();
        sprites = spritesheetUtils.spritesheetToSprites("../assets/spritesheet.png");
    }

    public void update() {

    }

    public void pickUp() {
        // FIXME - make pickups work
        if (powerupType == PowerupType.HEALTH) {
            panel.getPlayer().setPLAYER_HEALTH(panel.getPlayer().getPLAYER_HEALTH() + 1);
        } else if (powerupType == PowerupType.SHIELD) {
            panel.getPlayer().setPLAYER_SHIELD(panel.getPlayer().getPLAYER_SHIELD() + 1);
        }
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(sprites[4][powerupTypeInt], (int) x, (int) y, Config.tileComputed, Config.tileComputed, null);
    }


}
