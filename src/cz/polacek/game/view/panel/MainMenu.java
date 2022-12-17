package cz.polacek.game.view.panel;

import cz.polacek.game.config.Config;
import cz.polacek.game.view.Panel;
import cz.polacek.game.view.PanelState;
import cz.polacek.game.view.entity.GUITextEntity;
import cz.polacek.game.view.entity.Powerup;

import java.awt.*;
import java.util.ArrayList;

public class MainMenu {

    Panel panel;

    public MainMenu(Panel panel) {
        this.panel = panel;
    }

    public void update() {
        GUITextEntity[] textEntities = new GUITextEntity[]{
                new GUITextEntity("PRESS e TO START", 1, Config.windowWidth / 2 - Config.tileComputed * 8, Config.windowHeight / 2 - Config.tileComputed),
                new GUITextEntity("kHIGH SCORE:", 1, 0, 0),
                new GUITextEntity(Integer.toString(panel.getHighScore()), 1, 0, Config.tileComputed)
        };
        panel.getGameBackground().setY(Config.tileComputed / 2);
        panel.getGameBackground().setX(Config.tileComputed / 2);
        panel.getGui().setTextEntities(textEntities);
        if (panel.getKeyHandler().enterPressed) {
            panel.setEnemies(new ArrayList<>());
            panel.setPowerup(new Powerup(panel, panel.getPlayer(), 0, 0, 0));
            panel.setPanelState(PanelState.GAME);
        }
    }

    public void draw(Graphics2D graphics2D) {
        panel.getGameBackground().draw(graphics2D);
        panel.getGui().draw(graphics2D);
    }

}
