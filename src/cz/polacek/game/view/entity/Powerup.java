package cz.polacek.game.view.entity;

import cz.polacek.game.config.Config;
import cz.polacek.game.view.Panel;
import cz.polacek.game.view.Window;
import cz.polacek.game.view.entity.player.Player;

import java.awt.*;

public class Powerup extends Entity {

    Player player;
    Panel panel;
    PowerupType powerupType;
    int powerupTypeInt;

    public Powerup(Panel panel, Player player, double x, double y, PowerupType powerupType) {
        this.panel = panel;
        this.player = player;
        this.x = x;
        this.y = y;
        this.powerupType = powerupType;
        this.powerupTypeInt = powerupType.ordinal();
    }
    public Powerup(Panel panel, Player player, double x, double y, int powerupTypeInt) {
        this.panel = panel;
        this.player = player;
        this.x = x;
        this.y = y;
        switch (powerupTypeInt) {
            case 1 -> this.powerupType = PowerupType.HEALTH;
            case 2 -> this.powerupType = PowerupType.SHIELD;
            case 3 -> this.powerupType = PowerupType.BULLET;
            case 4 -> this.powerupType = PowerupType.MONEY;
            default -> this.powerupType = PowerupType.RANDOM;
        }
        this.powerupTypeInt = powerupType.ordinal();
    }

    public void pickUp() {
        if (powerupType == PowerupType.HEALTH) {
            panel.getPlayer().setPLAYER_HEALTH(panel.getPlayer().getPLAYER_HEALTH() + 1);
            this.x = -100;
        } else if (powerupType == PowerupType.SHIELD) {
            panel.getPlayer().setPLAYER_SHIELD(panel.getPlayer().getPLAYER_SHIELD() + 1);
            this.x = -100;
        } else if (powerupType == PowerupType.BULLET) {
            panel.getPlayer().setBulletIntervalInterval(Config.bulletFasterInterval);
            this.x = -100;
        } else if (powerupType == PowerupType.MONEY) {
            panel.setScore(panel.getScore() + Config.moneyPoints);
            this.x = -100;
        } else {
            // FIXME - make random powerup
            this.powerupType = PowerupType.MONEY;
            pickUp();
        }
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(sprites[powerupTypeInt][4], (int) x, (int) y, Config.tileComputed, Config.tileComputed, null);
    }


}
