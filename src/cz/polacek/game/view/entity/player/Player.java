package cz.polacek.game.view.entity.player;

import cz.polacek.game.config.Config;
import cz.polacek.game.utils.SpritesheetUtils;
import cz.polacek.game.view.Panel;
import cz.polacek.game.view.entity.Entity;
import cz.polacek.game.view.keylistener.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    SpritesheetUtils spritesheetUtils = new SpritesheetUtils();

    Panel panel;
    KeyHandler keyHandler;
    BufferedImage[][] sprites;

    Thread bulletInterval = new Thread(new BulletInterval());
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    double xBulletVel,yBulletVel = 5;

    int PLAYER_HEALTH = 3;
    int PLAYER_SHIELD = 0;

    Face playerFace;

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
        bulletInterval.start();
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
            playerFace = Face.UP_RIGHT;
        } else if (keyHandler.upPressed && keyHandler.leftPressed) {
            sprite_X = 5;
            playerFace = Face.UP_LEFT;
        } else if (keyHandler.downPressed && keyHandler.leftPressed) {
            sprite_X = 6;
            playerFace = Face.DOWN_LEFT;
        } else if (keyHandler.downPressed && keyHandler.rightPressed) {
            sprite_X = 7;
            playerFace = Face.DOWN_RIGHT;
        } else if (keyHandler.upPressed) {
            sprite_X = 0;
            playerFace = Face.UP;
        } else if (keyHandler.downPressed) {
            sprite_X = 2;
            playerFace = Face.DOWN;
        } else if (keyHandler.rightPressed) {
            sprite_X = 1;
            playerFace = Face.RIGHT;
        } else if (keyHandler.leftPressed) {
            sprite_X = 3;
            playerFace = Face.LEFT;
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

        if (keyHandler.spacePressed) {
            if (!bulletInterval.isAlive()) {
                bullets.add(new Bullet(x,y,playerFace));
                bulletInterval.start();
            }
        }
        System.out.println(bulletInterval.isAlive());
    }

    public void draw(Graphics2D graphics2D) {
        if (!bullets.isEmpty()) {
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                switch (bullet.getFace()) {
                    case UP -> {
                        xBulletVel = 0;
                        yBulletVel = -Config.bulletSpeed;
                    }
                    case DOWN -> {
                        xBulletVel = 0;
                        yBulletVel = Config.bulletSpeed;
                    }
                    case LEFT -> {
                        xBulletVel = -Config.bulletSpeed;
                        yBulletVel = 0;
                    }
                    case RIGHT -> {
                        xBulletVel = Config.bulletSpeed;
                        yBulletVel = 0;
                    }
                    case UP_LEFT -> {
                        xBulletVel = -Config.bulletSpeed;
                        yBulletVel = -Config.bulletSpeed;
                    }
                    case UP_RIGHT -> {
                        xBulletVel = Config.bulletSpeed;
                        yBulletVel = -Config.bulletSpeed;
                    }
                    case DOWN_LEFT -> {
                        xBulletVel = -Config.bulletSpeed;
                        yBulletVel = Config.bulletSpeed;
                    }
                    case DOWN_RIGHT -> {
                        xBulletVel = Config.bulletSpeed;
                        yBulletVel = Config.bulletSpeed;
                    }
                }
                bullet.setX((bullet.getX() + xBulletVel));
                bullet.setY((bullet.getY() + yBulletVel));
                graphics2D.drawImage(sprites[0][3], (int) bullet.getX(), (int) bullet.getY(), Config.tileComputed, Config.tileComputed, null);
                if (bullet.x > (Config.windowWidth + Config.tileComputed) || bullet.x < (-Config.tileComputed) || bullet.y > (Config.windowHeight + Config.tileComputed) || bullet.y < (-Config.tileComputed)) {
                    bullets.remove(i);
                }
            }
        }
        graphics2D.drawImage(sprites[sprite_X][sprite_Y], (int) x, (int) y, Config.tileComputed, Config.tileComputed, null);
    }
}
