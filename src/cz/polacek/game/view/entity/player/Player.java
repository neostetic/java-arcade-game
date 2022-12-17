package cz.polacek.game.view.entity.player;

import cz.polacek.game.config.Config;
import cz.polacek.game.utils.Utils;
import cz.polacek.game.view.Panel;
import cz.polacek.game.view.entity.Entity;
import cz.polacek.game.view.entity.Interval;
import cz.polacek.game.view.handler.KeyHandler;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity {

    Panel panel;
    KeyHandler keyHandler;

    int bulletIntervalInterval = Config.bulletInterval;

    Interval bulletInterval = new Interval(bulletIntervalInterval);
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    double xBulletVel, yBulletVel = 5;

    public void setBulletIntervalInterval(int bulletIntervalInterval) {
        bulletInterval.setInterval(bulletIntervalInterval);
    }

    public int getBulletIntervalInterval() {
        return bulletIntervalInterval;
    }

    int PLAYER_HEALTH = Config.maxPlayerHealth;
    int PLAYER_SHIELD = Config.maxPlayerShield;

    Face playerFace;

    public int getPLAYER_HEALTH() {
        return PLAYER_HEALTH;
    }

    public void setPLAYER_HEALTH(int PLAYER_HEALTH) {
        this.PLAYER_HEALTH = Math.min(PLAYER_HEALTH, Config.maxPlayerHealth);
    }

    public int getPLAYER_SHIELD() {
        return PLAYER_SHIELD;
    }

    public void setPLAYER_SHIELD(int PLAYER_SHIELD) {
        this.PLAYER_SHIELD = Math.min(PLAYER_SHIELD, Config.maxPlayerShield);
    }

    int sprite_X = 0;
    int sprite_Y = 0;
    private int deadSwitcherino = 0;

    public Player(Panel panel, KeyHandler keyHandler) {
        this.panel = panel;
        this.keyHandler = keyHandler;
        x = (Config.windowWidth / 2) - Config.tileComputed / 2;
        y = (Config.windowHeight / 2) - Config.tileComputed / 2;
        bulletInterval.start();
    }

    public void update() {
        if (getPLAYER_HEALTH() > 0) {

            if (keyHandler.upPressed) {
                if (yVel <= -Config.playerSpeed) {
                    yVel = -Config.playerSpeed;
                } else {
                    yVel = yVel - Config.playerSpeed / Config.playerSpeedSlowdown;
                }
            }
            if (keyHandler.downPressed) {
                if (yVel >= Config.playerSpeed) {
                    yVel = Config.playerSpeed;
                } else {
                    yVel = yVel + Config.playerSpeed / Config.playerSpeedSlowdown;
                }
            }
            if (keyHandler.leftPressed) {
                if (xVel <= -Config.playerSpeed) {
                    xVel = -Config.playerSpeed;
                } else {
                    xVel = xVel - Config.playerSpeed / Config.playerSpeedSlowdown;
                }
            }
            if (keyHandler.rightPressed) {
                if (xVel >= Config.playerSpeed) {
                    xVel = Config.playerSpeed;
                } else {
                    xVel = xVel + Config.playerSpeed / Config.playerSpeedSlowdown;
                }
            }

            if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
                sprite_Y = 0;
            } else {
                sprite_Y = 1;
            }

            if (y < 0) {
                y = 0;
            }
            if (y > (Config.windowHeight - Config.tileComputed)) {
                y = (Config.windowHeight - Config.tileComputed);
            }
            if (x < 0) {
                x = 0;
            }
            if (x + Config.tileComputed > (Config.windowWidth)) {
                x = (Config.windowWidth - Config.tileComputed);
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

            if (keyHandler.spacePressed) {
                if (bulletInterval.canDo()) {
                    bullets.add(new Bullet((int) x, (int) y, playerFace));
                    bulletInterval.did();
                }
            }
        } else {
            sprite_Y = 2;
            deadSwitcherino++;
        }
        yVel = yVel / Config.gravity;
        xVel = xVel / Config.gravity;
        y = y + yVel;
        x = x + xVel;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void getHit() {
        if (PLAYER_SHIELD != 0) {
            setPLAYER_SHIELD(getPLAYER_SHIELD() - 1);
        } else {
            setPLAYER_HEALTH(getPLAYER_HEALTH() - 1);
        }
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
                graphics2D.drawImage(sprites[Utils.randomNumberBetween(0,3)][3], (int) bullet.getX(), (int) bullet.getY(), Config.tileComputed, Config.tileComputed, null);
                if (bullet.x > (Config.windowWidth + Config.tileComputed) || bullet.x < (-Config.tileComputed) || bullet.y > (Config.windowHeight + Config.tileComputed) || bullet.y < (-Config.tileComputed)) {
                    bullets.remove(i);
                }
                // if (bullet.getRect().intersects())
            }
        }
        if (getPLAYER_HEALTH() > 0) {
            graphics2D.drawImage(sprites[sprite_X][sprite_Y], (int) x, (int) y, Config.tileComputed, Config.tileComputed, null);
        } else {
            graphics2D.drawImage(sprites[deadSwitcherino%8][sprite_Y], (int) x, (int) y, Config.tileComputed, Config.tileComputed, null);
        }
    }
}
