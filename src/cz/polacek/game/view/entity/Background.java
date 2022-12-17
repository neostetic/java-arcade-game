package cz.polacek.game.view.entity;

import cz.polacek.game.config.Config;
import cz.polacek.game.utils.Utils;
import cz.polacek.game.view.Panel;
import cz.polacek.game.view.entity.player.Player;

import java.awt.*;

public class Background extends Entity {

    Panel panel;
    Player player;

    double x2,y2;

    public Background(Panel panel, Player player) {
        this.panel = panel;
        this.player = player;
        x = 0;
        y = 0;
    }

    public void update() {
        player.yVel = player.yVel / Config.gravity;
        player.xVel = player.xVel / Config.gravity;

        x = x - player.xVel / 4;
        y = y - player.yVel / 4;
        x2 = x / 2;
        y2 = y / 2;
    }

    int[] randomArray1 = Utils.randomArray(1000, 1,4);
    int[] randomArray2 = Utils.randomArray(1000, 4,7);

    public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(sprites[0][8], 0, 0, Config.windowWidth, Config.windowHeight, null);
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
