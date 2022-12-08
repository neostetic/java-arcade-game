package cz.polacek.game.view.entity;

import cz.polacek.game.config.Config;
import cz.polacek.game.utils.SpritesheetUtils;
import cz.polacek.game.view.Panel;
import cz.polacek.game.view.keylistener.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GUI extends Entity {
    SpritesheetUtils spritesheetUtils = new SpritesheetUtils();

    Panel panel;
    KeyHandler keyHandler;
    Player player;
    public BufferedImage[][] sprites;

    int heart1 = 1;
    int heart2 = 1;
    int heart3 = 1;

    public GUI(Panel panel, KeyHandler keyHandler, Player player) {
        this.panel = panel;
        this.keyHandler = keyHandler;
        this.player = player;
        sprites = spritesheetUtils.spritesheetToSprites("../assets/spritesheet.png");
    }

    public void update() {
        switch (player.getPLAYER_HEALTH()) {
            case 3 -> {
                heart1 = 0;
                heart2 = 0;
                heart3 = 0;
            }
            case 2 -> {
                heart1 = 0;
                heart2 = 0;
                heart3 = 1;
            }
            case 1 -> {
                heart1 = 0;
                heart2 = 1;
                heart3 = 1;
            }
            default -> {
                heart1 = 1;
                heart2 = 1;
                heart3 = 1;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(sprites[heart1][6], 0, Config.windowHeight - Config.tileComputed, Config.tileComputed, Config.tileComputed, null);
        graphics2D.drawImage(sprites[heart2][6], Config.tileComputed, Config.windowHeight - Config.tileComputed, Config.tileComputed, Config.tileComputed, null);
        graphics2D.drawImage(sprites[heart3][6], Config.tileComputed * 2, Config.windowHeight - Config.tileComputed, Config.tileComputed, Config.tileComputed, null);
    }
}
