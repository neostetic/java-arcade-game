package cz.polacek.game.view.entity;

import cz.polacek.game.config.Config;
import cz.polacek.game.utils.SpritesheetUtils;
import cz.polacek.game.utils.Utils;
import cz.polacek.game.view.Panel;
import cz.polacek.game.view.keylistener.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends Entity {

    SpritesheetUtils spritesheetUtils = new SpritesheetUtils();

    Panel panel;
    KeyHandler keyHandler;
    public BufferedImage[][] sprites;

    double x2,y2;

    public Background(Panel panel, KeyHandler keyHandler) {
        this.panel = panel;
        this.keyHandler = keyHandler;
        x = 0;
        y = 0;
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

        x = x - xVel / 4;
        y = y - yVel / 4;
        x2 = x/2;
        y2 = y/2;
    }

    int[] randomArray1 = Utils.randomArray(1000, 1,4);
    int[] randomArray2 = Utils.randomArray(1000, 4,7);

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < Config.windowWidth / Config.tileComputed; i++) {
            for (int j = 0; j < Config.windowHeight / Config.tileComputed; j++) {
                graphics2D.drawImage(sprites[0][8], Config.tileComputed * i, Config.tileComputed * j, Config.tileComputed, Config.tileComputed, null);
            }
        }
        for (int i = 0; i < Config.windowWidth / Config.tileComputed + 2; i++) {
            for (int j = 0; j < Config.windowHeight / Config.tileComputed + 2; j++) {
                int computedX = (int) x + Config.tileComputed * i;
                int computedY = (int) y + Config.tileComputed * j;
                if (computedX >= Config.windowWidth + Config.tileComputed) {
                    computedX = computedX - Config.windowWidth - Config.tileComputed * 2;
                } else if (computedX <= -Config.tileComputed) {
                    computedX = computedX + Config.windowWidth + Config.tileComputed * 2;
                }
                if (computedY >= Config.windowHeight + Config.tileComputed) {
                    computedY = computedY - Config.windowHeight - Config.tileComputed * 2;
                } else if (computedY <= -Config.tileComputed) {
                    computedY = computedY + Config.windowHeight + Config.tileComputed * 2;
                }
                graphics2D.drawImage(sprites[randomArray1[i + j * 10]][8], computedX, computedY, Config.tileComputed, Config.tileComputed, null);
            }
        }
        for (int i = 0; i < Config.windowWidth / Config.tileComputed + 2; i++) {
            for (int j = 0; j < Config.windowHeight / Config.tileComputed + 2; j++) {
                int computedX = (int) x2 + Config.tileComputed * i;
                int computedY = (int) y2 + Config.tileComputed * j;
                if (computedX >= Config.windowWidth + Config.tileComputed) {
                    computedX = computedX - Config.windowWidth - Config.tileComputed * 2;
                } else if (computedX <= -Config.tileComputed) {
                    computedX = computedX + Config.windowWidth + Config.tileComputed * 2;
                }
                if (computedY >= Config.windowHeight + Config.tileComputed) {
                    computedY = computedY - Config.windowHeight - Config.tileComputed * 2;
                } else if (computedY <= -Config.tileComputed) {
                    computedY = computedY + Config.windowHeight + Config.tileComputed * 2;
                }
                graphics2D.drawImage(sprites[randomArray2[i + j * 10]][8], computedX, computedY, Config.tileComputed, Config.tileComputed, null);
            }
        }
    }
}
