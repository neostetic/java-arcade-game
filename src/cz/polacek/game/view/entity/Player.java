package cz.polacek.game.view.entity;

import cz.polacek.game.config.Config;
import cz.polacek.game.utils.SpritesheetUtils;
import cz.polacek.game.view.Panel;
import cz.polacek.game.view.keylistener.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    SpritesheetUtils spritesheetUtils = new SpritesheetUtils();

    Panel panel;
    KeyHandler keyHandler;
    BufferedImage[][] sprites;

    int PLAYER_HEALTH = 3;
    int PLAYER_SHIELD = 0;

    public int getPLAYER_HEALTH() {
        return PLAYER_HEALTH;
    }

    public void setPLAYER_HEALTH(int PLAYER_HEALTH) {
        this.PLAYER_HEALTH = PLAYER_HEALTH;
    }

    public int getPLAYER_SHIELD() {
        return PLAYER_SHIELD;
    }

    public void setPLAYER_SHIELD(int PLAYER_SHIELD) {
        this.PLAYER_SHIELD = PLAYER_SHIELD;
    }

    int sprite_X = 0;
    int sprite_Y = 0;

    public Player(Panel panel, KeyHandler keyHandler) {
        this.panel = panel;
        this.keyHandler = keyHandler;
        x = (Config.windowWidth / 2) - Config.tileComputed / 2;
        y = (Config.windowHeight / 2) - Config.tileComputed / 2;
        sprites = spritesheetUtils.spritesheetToSprites("../assets/spritesheet.png");
    }

    public void update() {
        if (keyHandler.upPressed) {
            if (yVel <= -Config.playerSpeed) {
                yVel = -Config.playerSpeed;
            } else {
                yVel = yVel - Config.playerSpeed/Config.playerSpeedSlowdown;
            }
        }
        if (keyHandler.downPressed) {
            if (yVel >= Config.playerSpeed) {
                yVel = Config.playerSpeed;
            } else {
                yVel = yVel + Config.playerSpeed/Config.playerSpeedSlowdown;
            }
        }
        if (keyHandler.leftPressed) {
            if (xVel <= -Config.playerSpeed) {
                xVel = -Config.playerSpeed;
            } else {
                xVel = xVel - Config.playerSpeed/Config.playerSpeedSlowdown;
            }
        }
        if (keyHandler.rightPressed) {
            if (xVel >= Config.playerSpeed) {
                xVel = Config.playerSpeed;
            } else {
                xVel = xVel + Config.playerSpeed/Config.playerSpeedSlowdown;
            }
        }
        yVel = yVel/Config.gravity;
        xVel = xVel/Config.gravity;

        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            sprite_Y = 0;
        } else {
            sprite_Y = 1;
        }

        if (keyHandler.upPressed && keyHandler.rightPressed) {
            sprite_X = 4;
        } else if (keyHandler.upPressed && keyHandler.leftPressed) {
            sprite_X = 5;
        } else if (keyHandler.downPressed && keyHandler.leftPressed) {
            sprite_X = 6;
        } else if (keyHandler.downPressed && keyHandler.rightPressed) {
            sprite_X = 7;
        } else if (keyHandler.upPressed) {
            sprite_X = 0;
        } else if (keyHandler.downPressed) {
            sprite_X = 2;
        } else if (keyHandler.rightPressed) {
            sprite_X = 1;
        } else if (keyHandler.leftPressed) {
            sprite_X = 3;
        }

        y = y + yVel;
        x = x + xVel;

        if (y < 0) { y = 0; }
        if (y > (Config.windowHeight - Config.tileComputed)) {
            y = (Config.windowHeight - Config.tileComputed);
        }
        if (x < 0) { x = 0; }
        if (x + Config.tileComputed > (Config.windowWidth)) {
            x = (Config.windowWidth - Config.tileComputed);
        }
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(sprites[sprite_X][sprite_Y], (int) x, (int) y, Config.tileComputed, Config.tileComputed, null);
    }
}
